package com.agendalc.agendalc.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.agendalc.agendalc.entities.Documento;
import com.agendalc.agendalc.entities.DocumentosEliminados;
import com.agendalc.agendalc.entities.DocumentosTramite;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.DocumentoRepository;
import com.agendalc.agendalc.repositories.DocumentosEliminadosRepository;
import com.agendalc.agendalc.repositories.DocumentosTramiteRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.services.interfaces.ArchivoService;
import com.agendalc.agendalc.services.interfaces.SolicitudDocumentoService;
import com.agendalc.agendalc.utils.RepositoryUtils;

@Service
@Transactional
public class SolicitudDocumentoServiceImpl implements SolicitudDocumentoService {

    private final ArchivoService archivoService;
    private final DocumentoRepository documentoRepository;
    private final DocumentosTramiteRepository documentosTramiteRepository;
    private final DocumentosEliminadosRepository documentosEliminadosRepository;
    private final SolicitudRepository solicitudRepository;

    public SolicitudDocumentoServiceImpl(ArchivoService archivoService, DocumentoRepository documentoRepository,
            DocumentosTramiteRepository documentosTramiteRepository,
            DocumentosEliminadosRepository documentosEliminadosRepository,
            SolicitudRepository solicitudRepository) {
        this.archivoService = archivoService;
        this.documentoRepository = documentoRepository;
        this.documentosTramiteRepository = documentosTramiteRepository;
        this.documentosEliminadosRepository = documentosEliminadosRepository;
        this.solicitudRepository = solicitudRepository;
    }

    @Override
    public void guardarDocumentosNuevos(Solicitud solicitud, Tramite tramite, MultipartFile[] files, List<Long> idTiposDocumentos)
            throws IOException {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            Long idTipoDocumento = idTiposDocumentos.get(i);

            if (file != null && !file.isEmpty()) {
                DocumentosTramite tipoDocumentoRequerido = RepositoryUtils.findOrThrow(
                        documentosTramiteRepository.findById(idTipoDocumento),
                        "Tipo de documento requerido con ID " + idTipoDocumento + " no encontrado.");

                String nombreGuardado = archivoService.guardarArchivo(file);
                Path rutaCompleta = archivoService.getRutaCompletaArchivo(nombreGuardado);

                Documento documento = new Documento();
                documento.setRutPersona(solicitud.getRut().toString());
                documento.setTramite(tramite);
                documento.setDocumentosTramite(tipoDocumentoRequerido);
                documento.setNombreArchivo(file.getOriginalFilename());
                documento.setPathStorage(rutaCompleta.toString());
                documento.setTipoMime(file.getContentType());
                documento.setOrigenId(solicitud.getIdSolicitud());
                documento.setOrigenTipo("SOLICITUD");

                documentoRepository.save(documento);
            }
        }
    }

    @Override
    public void reemplazarDocumento(Long idSolicitud, Long idDocumento, MultipartFile file) throws IOException {
        Documento docToReplace = getDocumentoById(idDocumento);
        verificarPertenenciaDocumento(docToReplace, idSolicitud);

        String oldPath = docToReplace.getPathStorage();
        actualizarArchivo(docToReplace, file);

        registrarReemplazo(oldPath, idSolicitud, idDocumento);

        documentoRepository.save(docToReplace);
    }

    private Documento getDocumentoById(Long idDocumento) {
        return RepositoryUtils.findOrThrow(documentoRepository.findById(idDocumento),
                String.format("Documento con id %d no encontrado.", idDocumento));
    }

    private void verificarPertenenciaDocumento(Documento documento, Long idSolicitud) {
        if (!documento.getOrigenId().equals(idSolicitud) || !"SOLICITUD".equals(documento.getOrigenTipo())) {
            throw new SecurityException("El documento no pertenece a la solicitud especificada.");
        }
    }

    private void actualizarArchivo(Documento documento, MultipartFile file) throws IOException {
        String nombreGuardado = archivoService.guardarArchivo(file);
        Path rutaCompleta = archivoService.getRutaCompletaArchivo(nombreGuardado);

        documento.setPathStorage(rutaCompleta.toString());
        documento.setNombreArchivo(file.getOriginalFilename());
        documento.setTipoMime(file.getContentType());
    }

    private void registrarReemplazo(String oldPath, Long idSolicitud, Long idDocumento) {
        documentosEliminadosRepository.save(new DocumentosEliminados(oldPath, idSolicitud, idDocumento));
    }

    @Override
    public void uploadDocumentosToSolicitud(Long solicitudId, List<Long> idTiposDocumentos, MultipartFile[] files) throws IOException {
        if (files == null || idTiposDocumentos == null || files.length != idTiposDocumentos.size()) {
            throw new IllegalArgumentException("La lista de archivos y de tipos de documento no puede estar vacía y deben tener el mismo tamaño.");
        }

        Solicitud solicitud = RepositoryUtils.findOrThrow(
                solicitudRepository.findById(solicitudId),
                "Solicitud no encontrada con id: " + solicitudId);

        Tramite tramite = solicitud.getTramite();
        if (tramite == null) {
            throw new jakarta.persistence.EntityNotFoundException("La solicitud con id " + solicitudId + " no tiene un trámite asociado.");
        }

        guardarDocumentosNuevos(solicitud, tramite, files, idTiposDocumentos);
    }
}

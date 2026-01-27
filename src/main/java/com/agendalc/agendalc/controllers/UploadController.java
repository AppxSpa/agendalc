package com.agendalc.agendalc.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.agendalc.agendalc.services.interfaces.SolicitudDocumentoService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/agendalc/upload")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class UploadController {

    private final SolicitudDocumentoService solicitudDocumentoService;

    public UploadController(SolicitudDocumentoService solicitudDocumentoService) {
        this.solicitudDocumentoService = solicitudDocumentoService;
    }

    @PreAuthorize("hasRole('FUNC')")
    @PostMapping("/solicitud-documentos")
    public ResponseEntity<Object> uploadSolicitudDocumentos(
            @RequestParam("solicitudId") Long solicitudId,
            @RequestParam("idTiposDocumentos") List<Long> idTiposDocumentos,
            @RequestParam("files") MultipartFile[] files) {
        try {
            solicitudDocumentoService.uploadDocumentosToSolicitud(solicitudId, idTiposDocumentos, files);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Documentos subidos y asociados con éxito."));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar y guardar los documentos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado: " + e.getMessage(), e);
        }
    }
}

package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.dto.CitaDelDiaResponseDto;
import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.dto.DetalleAgendamientoContribuyente;
import com.agendalc.agendalc.dto.DocumentoDto;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.exceptions.NotFounException;
import com.agendalc.agendalc.services.interfaces.ArchivoService;
import com.agendalc.agendalc.services.interfaces.CitaService;
import com.agendalc.agendalc.services.interfaces.DashboardService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class CitaController {

    private static final String ERROR_KEY = "error";

    private final CitaService citaService;
    private final ArchivoService archivoService;
    private final ObjectMapper objectMapper;
    private final DashboardService dashboardService;

    public CitaController(CitaService citaService, ArchivoService archivoService, ObjectMapper objectMapper,
            DashboardService dashboardService) {
        this.citaService = citaService;
        this.archivoService = archivoService;
        this.objectMapper = objectMapper;
        this.dashboardService = dashboardService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CitaDto> crearCita(@RequestBody CitaRequest citaRequest) {
        CitaDto nuevaCita = citaService.createCita(citaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    @PostMapping("/{id}/documentos")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> adjuntarDocumentos(
            @PathVariable Long id,
            @RequestParam("metadata") String metadataStr,
            @RequestParam(name = "documentos", required = false) List<MultipartFile> documentos)

            throws IOException {

        List<DocumentoDto> documentoDtos = objectMapper.readValue(metadataStr, new TypeReference<List<DocumentoDto>>() {
        });

        if (documentos != null && !documentos.isEmpty()) {
            Cita cita = citaService.findById(id);
            procesarYGuardarArchivos(documentoDtos, documentos, cita.getRut());
        }

        citaService.adjuntarDocumentos(id, documentoDtos);
        return ResponseEntity.ok().build();
    }

    private void procesarYGuardarArchivos(List<DocumentoDto> documentoDtos, List<MultipartFile> documentos, Integer rut)
            throws IOException {
        if (documentoDtos == null || documentoDtos.isEmpty()) {
            return;
        }

        Map<String, MultipartFile> fileMap = documentos.stream()
                .collect(Collectors.toMap(MultipartFile::getOriginalFilename, file -> file, (a, b) -> a));

        for (DocumentoDto docDto : documentoDtos) {
            MultipartFile file = fileMap.get(docDto.getNombreArchivo());
            if (file != null) {
                String nombreArchivoGuardado = archivoService.guardarArchivo(file);
                docDto.setPathStorage(nombreArchivoGuardado);
                docDto.setTipoMime(file.getContentType());
                docDto.setRutPersona(rut.toString());
            }
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getCita(@PathVariable Long id) {

        try {
            Cita cita = citaService.findById(id);
            return ResponseEntity.ok(cita);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/hoy/{rut}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'FUNC')")
    public ResponseEntity<Object> getCitaDeHoyPorRut(@PathVariable Integer rut) {
        try {
            CitaDelDiaResponseDto response = citaService.findCitaDelDiaPorRut(rut);
            return ResponseEntity.ok(response);
        } catch (NotFounException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(ERROR_KEY, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(ERROR_KEY, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Cita> updateCita(@PathVariable Long id, @RequestBody Cita cita) {
        Cita citaActualizada = citaService.updateCita(id, cita);
        return citaActualizada != null
                ? new ResponseEntity<>(citaActualizada, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        boolean eliminado = citaService.deleteCitaById(id);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findByRut/{rut}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getCitabyRut(@PathVariable Integer rut) {
        try {
            List<CitaDto> response = citaService.getCitaByRut(rut);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/entre-fechas")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getCitabyRutBetween(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        try {
            List<CitaDto> response = citaService.getCitaBetweenDates(fechaInicio, fechaFin);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/dias")
    public ResponseEntity<Object> getCitasDia(@RequestParam LocalDate fecha) {

        List<DetalleAgendamientoContribuyente> response = dashboardService.obtenerCitadosDeldia(fecha);

        if (response.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "No se encontraron citas"));

        return ResponseEntity.ok(response);

    }
}
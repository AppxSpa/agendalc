package com.agendalc.agendalc.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.agendalc.agendalc.dto.AprobeRejectRequest;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;
import com.agendalc.agendalc.services.interfaces.SolicitudService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/agendalc/solicitud")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class SolicitudController {

    private final SolicitudService solicitudService;
    private static String message = "message";

    public SolicitudController(SolicitudService solicitudCitaService) {
        this.solicitudService = solicitudCitaService;
    }

    @PreAuthorize("hasRole('FUNC')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SolicitudResponse> crearSolicitudConDocumentos(
            @RequestPart("solicitud") SolicitudRequest solicitudRequest,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {

        try {
            // La validación y construcción de la lista de documentos ahora se puede
            // manejar dentro del servicio
            // o mantenerse aquí si se prefiere una validación temprana.
            // Por simplicidad, pasamos los archivos directamente al servicio.
            SolicitudResponse response = solicitudService.createSolicitud(solicitudRequest, files);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al procesar y guardar los documentos adjuntos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ha ocurrido un error inesperado al crear la solicitud: " + e.getMessage(), e);
        }
    }

    @GetMapping("/list/{anio}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getSolicitudes(@PathVariable int anio) {

        try {
            List<SolicitudResponseList> response = solicitudService.getSolicitudes(anio);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/asignar")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> assignSolicitud(@RequestParam Long idSolicitud, @RequestParam String username,
            @RequestParam String asignadoA,
            @RequestParam int tipo) {
        try {
            solicitudService.assignOrDerivateSolicitud(idSolicitud, username, asignadoA, tipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(message, "Asignacion creada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/terminar/{id}/{login}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getSolicitudAssignById(@PathVariable Long id, @PathVariable String login) {

        try {
            solicitudService.finishSolicitudById(id, login);
            return ResponseEntity.status(HttpStatus.CREATED).body((Map.of(message, "Solicitud terminada con exito")));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/solicitudes-by-rut/{rut}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getSolicituCitasByRut(@PathVariable Integer rut) {

        try {
            List<SolicitudResponseList> response = solicitudService.getSolicitudesByRut(rut);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/asignadas/{rut}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getSolicitudesByRutFunc(@PathVariable Integer rut) {

        try {
            List<SolicitudResponseList> response = solicitudService.getSolicitudesByRutFunc(rut);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/solicitudes-entre-fechas")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getSolicitudessBetweenDatesAndState(@RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin, String estado) {

        EstadoSolicitud estadoSolicitud = EstadoSolicitud.valueOf(estado);

        try {
            List<SolicitudResponseList> response = solicitudService.getSolicitudesBetweenDatesAndState(fechaInicio,
                    fechaFin, estadoSolicitud);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PreAuthorize("hasRole('FUNC')")
    @PostMapping(value = "/replace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> replaceFile(@RequestParam Long idSolicitud, @RequestParam Long idTipo,
            @RequestParam MultipartFile file, @RequestParam String login) {

        try {
            solicitudService.replaceFile(idSolicitud, idTipo, file, login);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(message, "Archivo reemplazado con exito"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PreAuthorize("hasRole('FUNC')")
    @PostMapping("/aprobar")
    public ResponseEntity<Object> aprobeSolicitud(@RequestBody AprobeRejectRequest request) {

        try {
            solicitudService.aprobeSolicitud(request.getIdSolicitud(), request.getLogin());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(message, "Solicitud aprobada con exito"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PreAuthorize("hasRole('FUNC')")
    @PostMapping("/rechazar")
    public ResponseEntity<Object> rejectSolicitud(@RequestBody AprobeRejectRequest request) {

        try {
            solicitudService.rejectSolicitu(request.getIdSolicitud(), request.getLogin(), request.getMotivoRechazo());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(message, "Solicitud rechazada con exito"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> deleteSolicitud(@PathVariable Long id) {

        try {
            solicitudService.delteSolicitud(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(message, "Solicitud eliminada con exito"));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}

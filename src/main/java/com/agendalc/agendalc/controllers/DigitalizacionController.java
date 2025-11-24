
package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.dto.DigitalizacionRequest;
import com.agendalc.agendalc.services.interfaces.DigitalizacionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agendalc/digitalizacion")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class DigitalizacionController {

    private final DigitalizacionService digitalizacionService;

    public DigitalizacionController(DigitalizacionService digitalizacionService) {
        this.digitalizacionService = digitalizacionService;
    }

    @PreAuthorize("hasRole('FUNC')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> digitalizarDocumentos(
            @RequestPart("request") DigitalizacionRequest request,
            @RequestPart("files") List<MultipartFile> files) {

        if (files == null || files.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe adjuntar al menos un archivo.");
        }

        try {
            digitalizacionService.guardarDocumentosDigitalizados(request, files);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Documentos digitalizados y guardados correctamente."));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar los archivos: " + e.getMessage(), e);
        }
    }
}

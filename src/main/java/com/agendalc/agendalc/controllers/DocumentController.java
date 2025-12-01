package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.dto.DocumentoDto;
import com.agendalc.agendalc.services.interfaces.DocumentoService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.agendalc.agendalc.config.AppProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/agendalc/documents")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class DocumentController {

    private final AppProperties appProperties;
    private final DocumentoService documentoService;

    public DocumentController(AppProperties appProperties, DocumentoService documentoService) {
        this.appProperties = appProperties;
        this.documentoService = documentoService;
    }

    @GetMapping("/by-rut/{rut}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getDocumentosByRut(@PathVariable String rut) {
        try {
            List<DocumentoDto> response = documentoService.findByRut(rut);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        Path filePath = Paths.get(appProperties.getUploadDir()).resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = "application/octet-stream";
        try {
            String detectedType = Files.probeContentType(filePath);
            if (detectedType != null) {
                contentType = detectedType;
            }
        } catch (IOException ignored) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}

package com.agendalc.agendalc.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.dto.FilaAtencionResponseDto;
import com.agendalc.agendalc.services.interfaces.FilaAtencionService;

@RestController
@RequestMapping("/api/agendalc/filas-atencion")
public class FilaAtencionController {

    private final FilaAtencionService filaAtencionService;

    public FilaAtencionController(FilaAtencionService filaAtencionService) {
        this.filaAtencionService = filaAtencionService;
    }

    @PostMapping("/citas/{citaId}/iniciar")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Idealmente un rol 'RECEPCION'
    public ResponseEntity<List<FilaAtencionResponseDto>> iniciarProceso(@PathVariable Long citaId, @org.springframework.web.bind.annotation.RequestBody com.agendalc.agendalc.dto.IniciarProcesosRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filaAtencionService.iniciarProceso(citaId, request));
    }

    @GetMapping("/etapas/{etapaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<FilaAtencionResponseDto>> getFilaPorEtapa(@PathVariable Long etapaId) {
        return ResponseEntity.ok(filaAtencionService.verFilaPorEtapa(etapaId));
    }

    @GetMapping("/mis-filas")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<FilaAtencionResponseDto>> getMisFilas() {
        return ResponseEntity.ok(filaAtencionService.verMisFilasDeEspera());
    }

    @GetMapping("/mi-atencion-actual")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FilaAtencionResponseDto> getMiAtencionActual() {
        FilaAtencionResponseDto atencionActual = filaAtencionService.verAtencionActual();
        if (atencionActual == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(atencionActual);
    }

    @PostMapping("/etapas/{etapaId}/llamar")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FilaAtencionResponseDto> llamarSiguiente(@PathVariable Long etapaId) {
        return ResponseEntity.ok(filaAtencionService.llamarSiguiente(etapaId));
    }

    @PostMapping("/{filaId}/finalizar")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FilaAtencionResponseDto> finalizarAtencion(@PathVariable Long filaId) {
        return ResponseEntity.ok(filaAtencionService.finalizarAtencion(filaId));
    }

    @PostMapping("/llamar-persona/{filaId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FilaAtencionResponseDto> llamarPersona(@PathVariable Long filaId) {
        return ResponseEntity.ok(filaAtencionService.llamarPersona(filaId));
    }
}

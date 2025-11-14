package com.agendalc.agendalc.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.dto.UsuarioEtapaRequestDto;
import com.agendalc.agendalc.dto.UsuarioEtapaResponseDto;
import com.agendalc.agendalc.services.interfaces.UsuarioEtapaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario-etapas")
public class UsuarioEtapaController {

    private final UsuarioEtapaService usuarioEtapaService;

    public UsuarioEtapaController(UsuarioEtapaService usuarioEtapaService) {
        this.usuarioEtapaService = usuarioEtapaService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioEtapaResponseDto>> getAll() {
        return ResponseEntity.ok(usuarioEtapaService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioEtapaResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioEtapaService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UsuarioEtapaResponseDto>> getByUsuarioId(@PathVariable String usuarioId) {
        return ResponseEntity.ok(usuarioEtapaService.findByUsuarioId(usuarioId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioEtapaResponseDto> create(@Valid @RequestBody UsuarioEtapaRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioEtapaService.save(requestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioEtapaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

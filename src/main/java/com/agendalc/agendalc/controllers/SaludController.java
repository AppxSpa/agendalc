package com.agendalc.agendalc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.services.interfaces.SaludService;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/agendalc/salud")
public class SaludController {

    private final SaludService saludService;

    public SaludController(SaludService saludService) {
        this.saludService = saludService;
    }

    @PostMapping
    public ResponseEntity<SaludFormularioDto> guardarInformacionSalud(@RequestBody SaludFormularioDto request) {
        if (request.getRut() == null ) {
            return ResponseEntity.badRequest().build();
        }

        SaludFormularioDto saved = saludService.saveFormulario(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<SaludFormularioDto> buscarPorRut(@PathVariable String rut) {
        return saludService.findByRut(rut)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
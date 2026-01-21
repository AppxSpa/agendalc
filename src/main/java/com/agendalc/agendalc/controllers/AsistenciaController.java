package com.agendalc.agendalc.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import com.agendalc.agendalc.entities.AsistenciaCita;
import com.agendalc.agendalc.services.interfaces.AsistenciaCitaService;

@RestController
@RequestMapping("/api/agendalc/asistencia")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class AsistenciaController {

    private final AsistenciaCitaService asistenciaCitaService;

    public AsistenciaController(AsistenciaCitaService asistenciaCitaService) {
        this.asistenciaCitaService = asistenciaCitaService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> registrarAsistencia(@RequestParam Long citaId) {

        AsistenciaCita asistenciaCita = asistenciaCitaService.registrarAsistencia(citaId);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Asistencia registrada con éxito",
                "asistenciaCita", asistenciaCita));
    }

}

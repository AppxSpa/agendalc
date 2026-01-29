package com.agendalc.agendalc.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import com.agendalc.agendalc.services.interfaces.AsistenciaCitaService;
import com.agendalc.agendalc.services.interfaces.ResumenDiarioSevice;

@RestController
@RequestMapping("/api/agendalc/asistencia")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class AsistenciaController {

    private final AsistenciaCitaService asistenciaCitaService;
    private final ResumenDiarioSevice resumenDiarioSevice;

    public AsistenciaController(AsistenciaCitaService asistenciaCitaService, ResumenDiarioSevice resumenDiarioSevice) {
        this.asistenciaCitaService = asistenciaCitaService;
        this.resumenDiarioSevice = resumenDiarioSevice;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> registrarAsistencia(@RequestParam Long citaId) {

        asistenciaCitaService.registrarAsistencia(citaId);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Asistencia registrada con éxito"));
    }

    @GetMapping("/resumen-diario")
    public ResponseEntity<Object> resumenDiario(@RequestParam LocalDate fecha) {
        return ResponseEntity.ok(resumenDiarioSevice.resumendiario(fecha));
    }

}

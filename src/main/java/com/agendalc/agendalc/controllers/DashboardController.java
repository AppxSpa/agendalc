package com.agendalc.agendalc.controllers;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.services.interfaces.DashboardService;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@RestController
@RequestMapping("/api/agendalc/dashboard")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping()
    public ResponseEntity<Object> summary(@RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        return ResponseEntity.ok(dashboardService.obtenerIndicadoresDashboard(fechaInicio, fechaFin));
    }

    @GetMapping("/diarios")
    public ResponseEntity<Object> dailyIndicators(
            @RequestParam @NotNull @PastOrPresent

            LocalDate fecha,
            @RequestParam Long idTramite) {
        return ResponseEntity.ok(dashboardService.obtenerIndicadoresDiarios(fecha, idTramite));
    }

}

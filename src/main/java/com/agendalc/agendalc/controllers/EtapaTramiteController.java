package com.agendalc.agendalc.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.dto.EtapaTramiteRequest;
import com.agendalc.agendalc.dto.EtapaTramiteResponse;
import com.agendalc.agendalc.services.interfaces.EtapaTramiteService;

@RestController
@RequestMapping("/api/agendalc/etapas")
public class EtapaTramiteController {

    private final EtapaTramiteService etapaTramiteService;

    public EtapaTramiteController(EtapaTramiteService etapaTramiteService) {
        this.etapaTramiteService = etapaTramiteService;
    }

    @GetMapping("/tramite/{tramiteId}")
    public ResponseEntity<List<EtapaTramiteResponse>> getEtapasByTramiteId(@PathVariable Long tramiteId) {
        List<EtapaTramiteResponse> etapas = etapaTramiteService.findByTramiteId(tramiteId);
        return ResponseEntity.ok(etapas);
    }

    @PostMapping
    public ResponseEntity<List<EtapaTramiteResponse>> createEtapa(@RequestBody List<EtapaTramiteRequest> request) {
        List<EtapaTramiteResponse> createdEtapa = etapaTramiteService.create(request);
        return new ResponseEntity<>(createdEtapa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtapaTramiteResponse> updateEtapa(@PathVariable Long id,
            @RequestBody EtapaTramiteRequest request) {
        EtapaTramiteResponse updatedEtapa = etapaTramiteService.update(id, request);
        return ResponseEntity.ok(updatedEtapa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtapa(@PathVariable Long id) {
        etapaTramiteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

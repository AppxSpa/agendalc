package com.agendalc.agendalc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.DeclaracionSaludResponse;
import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.dto.SaludProfesionesDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludProfesion;
import com.agendalc.agendalc.repositories.SaludFormularioRepository;
import com.agendalc.agendalc.repositories.SaludProfesionRepository;
import com.agendalc.agendalc.services.interfaces.SaludService;
import com.agendalc.agendalc.services.interfaces.FirmaService;
import com.agendalc.agendalc.services.mappers.saludmappers.DeclaracionSaludResponseMapper;
import com.agendalc.agendalc.services.mappers.saludmappers.FormularioDtoMapper;
import com.agendalc.agendalc.services.mappers.saludmappers.SaludEntityMapper;
import com.agendalc.agendalc.utils.RutUtils;

@Service
public class SaludServiceImpl implements SaludService {

    private final SaludFormularioRepository formularioRepository;
    private final SaludEntityMapper saludEntityMapper;
    private final FormularioDtoMapper formularioDtoMapper;
    private final DeclaracionSaludResponseMapper declaracionSaludResponseMapper;
    private final FirmaService firmaService;
    private final SaludProfesionRepository saludProfesionRepository;

    public SaludServiceImpl(SaludFormularioRepository formularioRepository,
            SaludEntityMapper saludEntityMapper, FormularioDtoMapper formularioDtoMapper,
            DeclaracionSaludResponseMapper declaracionSaludResponseMapper,
            FirmaService firmaService, SaludProfesionRepository saludProfesionRepository) {
        this.formularioRepository = formularioRepository;
        this.saludEntityMapper = saludEntityMapper;
        this.formularioDtoMapper = formularioDtoMapper;
        this.declaracionSaludResponseMapper = declaracionSaludResponseMapper;
        this.firmaService = firmaService;
        this.saludProfesionRepository = saludProfesionRepository;
    }

    @Override
    @Transactional
    public SaludFormularioDto saveFormulario(SaludFormularioDto dto) {
        SaludFormulario f = mapToEntity(dto);
        SaludFormulario saved = formularioRepository.save(f);

        firmaService.saveFirma(dto.getFirma(), saved);

        return mapToDto(saved);
    }

    @Override
    public Optional<SaludFormularioDto> findByRut(String rut) {
        Integer r = RutUtils.parse(rut);
        if (r == null)
            return Optional.empty();
        Optional<SaludFormulario> opt = formularioRepository.findByRut(r);
        return opt.map(this::mapToDto);
    }

    private SaludFormulario mapToEntity(SaludFormularioDto dto) {
        return saludEntityMapper.mapToEntity(dto);
    }

    private SaludFormularioDto mapToDto(SaludFormulario f) {
        return formularioDtoMapper.mapToDto(f);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeclaracionSaludResponse> findDeclaracionesByRut(Integer rut) {

        if (rut == null) {
            return new ArrayList<>();
        }

        List<SaludFormulario> formularios = formularioRepository.findByRutOrderByFechaFormularioDesc(rut);
        return formularios.stream()
                .map(declaracionSaludResponseMapper::toDto)
                .toList();
    }

    @Override
    public List<SaludProfesionesDto> getProfesiones() {

        List<SaludProfesion> list = saludProfesionRepository.findAll();

        return list.stream().map(prof -> {
            SaludProfesionesDto dto = new SaludProfesionesDto();
            dto.setIdProfesion(prof.getId());
            dto.setNombre(prof.getNombre());
            return dto;
        }).toList();

    }
}

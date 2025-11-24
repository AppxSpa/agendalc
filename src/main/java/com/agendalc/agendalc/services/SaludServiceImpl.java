package com.agendalc.agendalc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.CitaAsociadaDto;
import com.agendalc.agendalc.dto.DeclaracionSaludResponse;
import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.dto.SolicitudAsociadaDto;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.SaludFirma;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.SaludFormularioRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.services.interfaces.SaludService;
import com.agendalc.agendalc.services.mappers.saludmappers.FirmaMapper;
import com.agendalc.agendalc.services.mappers.saludmappers.FormularioDtoMapper;
import com.agendalc.agendalc.services.mappers.saludmappers.SaludEntityMapper;

@Service
public class SaludServiceImpl implements SaludService {

    private final SaludFormularioRepository formularioRepository;
    private final SolicitudRepository solicitudRepository;
    private final CitaRepository citaRepository;
    private final SaludEntityMapper saludEntityMapper;
    private final FormularioDtoMapper formularioDtoMapper;
    private final FirmaMapper firmaMapper;
    private final com.agendalc.agendalc.repositories.SaludFirmaRepository saludFirmaRepository;

    public SaludServiceImpl(SaludFormularioRepository formularioRepository,
            SolicitudRepository solicitudRepository, CitaRepository citaRepository,
            SaludEntityMapper saludEntityMapper, FormularioDtoMapper formularioDtoMapper,
            FirmaMapper firmaMapper, com.agendalc.agendalc.repositories.SaludFirmaRepository saludFirmaRepository) {
        this.formularioRepository = formularioRepository;
        this.solicitudRepository = solicitudRepository;
        this.citaRepository = citaRepository;
        this.saludEntityMapper = saludEntityMapper;
        this.formularioDtoMapper = formularioDtoMapper;
        this.firmaMapper = firmaMapper;
        this.saludFirmaRepository = saludFirmaRepository;
    }

    private Integer parseRut(String rut) {
        if (rut == null)
            return null;
        String digits = rut.replaceAll("\\D", "");
        try {
            return Integer.valueOf(digits);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public SaludFormularioDto saveFormulario(SaludFormularioDto dto) {
        SaludFormulario f = mapToEntity(dto);
        SaludFormulario saved = formularioRepository.save(f);

        if (dto.getFirma() != null && !dto.getFirma().isEmpty()) {
            SaludFirma firma = firmaMapper.toEntity(dto.getFirma(), saved);
            saludFirmaRepository.save(firma);
            saved.setFirma(firma);
        }

        return mapToDto(saved);
    }

    @Override
    public Optional<SaludFormularioDto> findByRut(String rut) {
        Integer r = parseRut(rut);
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
    public List<DeclaracionSaludResponse> findDeclaracionesByRut(String rut) {
        Integer rutInt = parseRut(rut);
        if (rutInt == null) {
            return new ArrayList<>();
        }

        List<SaludFormulario> formularios = formularioRepository.findByRutOrderByFechaFormularioDesc(rutInt);
        List<DeclaracionSaludResponse> responseList = new ArrayList<>();

        for (SaludFormulario formulario : formularios) {
            DeclaracionSaludResponse response = new DeclaracionSaludResponse();
            response.setDeclaracion(mapToDto(formulario));

            // Buscar Solicitud asociada
            Optional<Solicitud> solicitudOpt = solicitudRepository.findBySaludFormulario(formulario);
            if (solicitudOpt.isPresent()) {
                Solicitud sol = solicitudOpt.get();
                response.setSolicitudAsociada(
                        new SolicitudAsociadaDto(sol.getIdSolicitud(), sol.getEstado().name(),
                                sol.getFechaSolicitud()));
            }

            // Buscar Cita asociada
            Optional<Cita> citaOpt = citaRepository.findBySaludFormulario(formulario);
            if (citaOpt.isPresent()) {
                Cita c = citaOpt.get();
                response.setCitaAsociada(
                        new CitaAsociadaDto(c.getIdCita(), c.getFechaHora(), c.getAgenda().getIdAgenda()));
            }

            responseList.add(response);
        }

        return responseList;
    }
}

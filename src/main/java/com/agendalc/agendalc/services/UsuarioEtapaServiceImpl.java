package com.agendalc.agendalc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.UsuarioEtapaRequestDto;
import com.agendalc.agendalc.dto.UsuarioEtapaResponseDto;
import com.agendalc.agendalc.entities.EtapaTramite;
import com.agendalc.agendalc.entities.UsuarioEtapa;
import com.agendalc.agendalc.exceptions.NotFounException;
import com.agendalc.agendalc.repositories.EtapaTramiteRepository;
import com.agendalc.agendalc.repositories.UsuarioEtapaRepository;
import com.agendalc.agendalc.services.interfaces.UsuarioEtapaService;

@Service
public class UsuarioEtapaServiceImpl implements UsuarioEtapaService {

    private final UsuarioEtapaRepository usuarioEtapaRepository;
    private final EtapaTramiteRepository etapaTramiteRepository;

    public UsuarioEtapaServiceImpl(UsuarioEtapaRepository usuarioEtapaRepository,
            EtapaTramiteRepository etapaTramiteRepository) {
        this.usuarioEtapaRepository = usuarioEtapaRepository;
        this.etapaTramiteRepository = etapaTramiteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEtapaResponseDto> findAll() {
        return usuarioEtapaRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioEtapaResponseDto findById(Long id) {
        UsuarioEtapa usuarioEtapa = usuarioEtapaRepository.findById(id)
                .orElseThrow(() -> new NotFounException("Asignación no encontrada con ID: " + id));
        return toResponseDto(usuarioEtapa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEtapaResponseDto> findByUsuarioId(String usuarioId) {
        return usuarioEtapaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public UsuarioEtapaResponseDto save(UsuarioEtapaRequestDto requestDto) {
        EtapaTramite etapaTramite = etapaTramiteRepository.findById(requestDto.getEtapaTramiteId())
                .orElseThrow(() -> new NotFounException(
                        "Etapa de trámite no encontrada con ID: " + requestDto.getEtapaTramiteId()));

        UsuarioEtapa usuarioEtapa = new UsuarioEtapa();
        usuarioEtapa.setUsuarioId(requestDto.getUsuarioId());
        usuarioEtapa.setNombreUsuario(requestDto.getNombreUsuario());
        usuarioEtapa.setEtapaTramite(etapaTramite);

        UsuarioEtapa savedUsuarioEtapa = usuarioEtapaRepository.save(usuarioEtapa);
        return toResponseDto(savedUsuarioEtapa);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!usuarioEtapaRepository.existsById(id)) {
            throw new NotFounException("Asignación no encontrada con ID: " + id);
        }
        usuarioEtapaRepository.deleteById(id);
    }

    private UsuarioEtapaResponseDto toResponseDto(UsuarioEtapa usuarioEtapa) {
        return new UsuarioEtapaResponseDto(
                usuarioEtapa.getId(),
                usuarioEtapa.getUsuarioId(),
                usuarioEtapa.getNombreUsuario(),
                usuarioEtapa.getEtapaTramite().getId(),
                usuarioEtapa.getEtapaTramite().getNombre());
    }
}

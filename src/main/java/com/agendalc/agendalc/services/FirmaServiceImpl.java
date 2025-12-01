package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;

import com.agendalc.agendalc.entities.SaludFirma;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.repositories.SaludFirmaRepository;
import com.agendalc.agendalc.services.interfaces.FirmaService;
import com.agendalc.agendalc.services.mappers.saludmappers.FirmaMapper;

@Service
public class FirmaServiceImpl implements FirmaService {

    private final SaludFirmaRepository saludFirmaRepository;
    private final FirmaMapper firmaMapper;

    public FirmaServiceImpl(SaludFirmaRepository saludFirmaRepository, FirmaMapper firmaMapper) {
        this.saludFirmaRepository = saludFirmaRepository;
        this.firmaMapper = firmaMapper;
    }

    @Override
    public void saveFirma(String firmaBase64, SaludFormulario formularioPadre) {
        if (firmaBase64 != null && !firmaBase64.isEmpty()) {
            SaludFirma firma = firmaMapper.toEntity(firmaBase64, formularioPadre);
            saludFirmaRepository.save(firma);
            formularioPadre.setFirma(firma);
        }
    }
}

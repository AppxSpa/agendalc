package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.entities.SaludFormulario;

public interface FirmaService {
    void saveFirma(String firmaBase64, SaludFormulario formularioPadre);
}

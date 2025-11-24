package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.TramiteRepository;

@Component
public class SaludEntityMapper {

    private final TramiteRepository tramiteRepository;

    private final PersonalesMapper personalesMapper;
    private final LicenciasMapper licenciasMapper;
    private final EstudiosMapper estudiosMapper;
    private final SituacionLaboralMapper situacionLaboralMapper;
    private final ProfesionMapper profesionMapper;
    private final JornadaMapper jornadaMapper;
    private final CardioMapper cardioMapper;
    private final OftalmologicoMapper oftalmologicoMapper;

    private final OtorrinoMapper otorrinoMapper;
    private final NeuroMapper neuroMapper;
    private final MotrizMapper motrizMapper;
    private final RespiratorioMapper respiratorioMapper;
    private final EndocrinoMapper endocrinoMapper;
    private final OncologicoMapper oncologicoMapper;
    private final InmunoMaper inmunoMaper;
    private final OtrosMapper otrosMapper;
    private final MedicamentosMapper medicamentosMapper;
    private final ConduccionMapper conduccionMapper;

    public SaludEntityMapper(TramiteRepository tramiteRepository,
            PersonalesMapper personalesMapper,
            LicenciasMapper licenciasMapper,
            EstudiosMapper estudiosMapper,
            SituacionLaboralMapper situacionLaboralMapper,
            ProfesionMapper profesionMapper,
            JornadaMapper jornadaMapper,
            CardioMapper cardioMapper,
            OftalmologicoMapper oftalmologicoMapper,
            OtorrinoMapper otorrinoMapper,
            NeuroMapper neuroMapper,
            MotrizMapper motrizMapper,
            RespiratorioMapper respiratorioMapper,
            EndocrinoMapper endocrinoMapper,
            OncologicoMapper oncologicoMapper,
            InmunoMaper inmunoMaper,
            OtrosMapper otrosMapper,
            MedicamentosMapper medicamentosMapper,
            ConduccionMapper conduccionMapper) {
        this.tramiteRepository = tramiteRepository;
        this.personalesMapper = personalesMapper;
        this.licenciasMapper = licenciasMapper;
        this.estudiosMapper = estudiosMapper;
        this.situacionLaboralMapper = situacionLaboralMapper;
        this.profesionMapper = profesionMapper;
        this.jornadaMapper = jornadaMapper;
        this.cardioMapper = cardioMapper;
        this.oftalmologicoMapper = oftalmologicoMapper;
        this.otorrinoMapper = otorrinoMapper;
        this.neuroMapper = neuroMapper;
        this.motrizMapper = motrizMapper;
        this.respiratorioMapper = respiratorioMapper;
        this.endocrinoMapper = endocrinoMapper;
        this.oncologicoMapper = oncologicoMapper;
        this.inmunoMaper = inmunoMaper;
        this.otrosMapper = otrosMapper;
        this.medicamentosMapper = medicamentosMapper;
        this.conduccionMapper = conduccionMapper;
    }

    public SaludFormulario mapToEntity(SaludFormularioDto dto) {
        SaludFormulario f = new SaludFormulario();
        f.setRut(dto.getRut());
        f.setFechaFormulario(dto.getFechaFormulario());

        if (dto.getIdTramite() != null) {
            Tramite tramite = tramiteRepository.findById(dto.getIdTramite())
                    .orElseThrow(() -> new RuntimeException("Tramite no encontrado con id: " + dto.getIdTramite()));
            f.setTramite(tramite);
        }

        // Llamar a mappers por responsabilidad
        personalesMapper.mapPersonales(dto, f);
        licenciasMapper.mapLicencias(dto, f);
        estudiosMapper.mapEstudios(dto, f);
        situacionLaboralMapper.mapSituacionLaboral(dto, f);
        profesionMapper.mapProfesion(dto, f);
        jornadaMapper.mapJornada(dto, f);
        cardioMapper.mapCardio(dto, f);
        oftalmologicoMapper.mapOftalmologico(dto, f);
        otorrinoMapper.mapOtorrino(dto, f);
        neuroMapper.mapNeurologico(dto, f);
        motrizMapper.mapMotriz(dto, f);
        respiratorioMapper.mapRespiratorio(dto, f);
        endocrinoMapper.mapEndocrino(dto, f);
        oncologicoMapper.mapOncologico(dto, f);
        inmunoMaper.mapInmunologico(dto, f);
        otrosMapper.mapOtros(dto, f);
        medicamentosMapper.mapMedicamentos(dto, f);
        conduccionMapper.mapConduccion(dto, f);

        return f;
    }

}

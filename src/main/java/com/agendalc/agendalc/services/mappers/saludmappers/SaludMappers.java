package com.agendalc.agendalc.services.mappers.saludmappers;

import org.springframework.stereotype.Component;

@Component
public class SaludMappers {

    final LicenciasDtoMapper licenciasDtoMapper;
    final EstudiosDtoMapper estudiosDtoMapper;
    final LaboralDtoMapper laboralDtoMapper;
    final ProfesionDtoMapper profesionDtoMapper;
    final JornadaDtoMapper jornadaDtoMapper;
    final OtorrinoDtoMapper otorrinoDtoMapper;
    final EndocrinoDtoMaper endocrinoDtoMaper;
    final OncologicoDtoMapper oncologicoDtoMapper;
    final InmunoDtoMapper inmunoDtoMapper;
    final PersonalesDtoMapper personalesDtoMapper;
    final CardioDtoMapper cardioDtoMapper;
    final OftalmologicoDtoMapper oftalmologicoDtoMapper;
    final NeuroDtoMapper neuroDtoMapper;
    final MotrizDtoMapper motrizDtoMapper;
    final RespiratorioDtoMapper respiratorioDtoMapper;
    final MedicamentosDtoMapper medicamentosDtoMapper;
    final ConduccionDtoMapper conduccionDtoMapper;
    final OtrosDtoMapper otrosDtoMapper;
    final FirmaDtoMapper firmaDtoMapper;

    public SaludMappers(LicenciasDtoMapper licenciasDtoMapper, EstudiosDtoMapper estudiosDtoMapper,
            LaboralDtoMapper laboralDtoMapper, ProfesionDtoMapper profesionDtoMapper, JornadaDtoMapper jornadaDtoMapper,
            OtorrinoDtoMapper otorrinoDtoMapper, EndocrinoDtoMaper endocrinoDtoMaper,
            OncologicoDtoMapper oncologicoDtoMapper,
            InmunoDtoMapper inmunoDtoMapper, PersonalesDtoMapper personalesDtoMapper, CardioDtoMapper cardioDtoMapper,
            OftalmologicoDtoMapper oftalmologicoDtoMapper, NeuroDtoMapper neuroDtoMapper,
            MotrizDtoMapper motrizDtoMapper,
            RespiratorioDtoMapper respiratorioDtoMapper,
            MedicamentosDtoMapper medicamentosDtoMapper,
            ConduccionDtoMapper conduccionDtoMapper,
            OtrosDtoMapper otrosDtoMapper,
            FirmaDtoMapper firmaDtoMapper) {
        this.licenciasDtoMapper = licenciasDtoMapper;
        this.estudiosDtoMapper = estudiosDtoMapper;
        this.laboralDtoMapper = laboralDtoMapper;
        this.profesionDtoMapper = profesionDtoMapper;
        this.jornadaDtoMapper = jornadaDtoMapper;
        this.otorrinoDtoMapper = otorrinoDtoMapper;
        this.endocrinoDtoMaper = endocrinoDtoMaper;
        this.oncologicoDtoMapper = oncologicoDtoMapper;
        this.inmunoDtoMapper = inmunoDtoMapper;
        this.personalesDtoMapper = personalesDtoMapper;
        this.cardioDtoMapper = cardioDtoMapper;
        this.oftalmologicoDtoMapper = oftalmologicoDtoMapper;
        this.neuroDtoMapper = neuroDtoMapper;
        this.motrizDtoMapper = motrizDtoMapper;
        this.respiratorioDtoMapper = respiratorioDtoMapper;
        this.medicamentosDtoMapper = medicamentosDtoMapper;
        this.conduccionDtoMapper = conduccionDtoMapper;
        this.otrosDtoMapper = otrosDtoMapper;
        this.firmaDtoMapper = firmaDtoMapper;
    }
}

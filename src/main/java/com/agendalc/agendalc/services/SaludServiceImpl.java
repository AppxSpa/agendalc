package com.agendalc.agendalc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.SaludConduccionDto;
import com.agendalc.agendalc.dto.SaludFormularioDto;
import com.agendalc.agendalc.dto.SaludMedicamentoDto;
import com.agendalc.agendalc.entities.SaludCardio;
import com.agendalc.agendalc.entities.SaludConduccion;
import com.agendalc.agendalc.entities.SaludFormulario;
import com.agendalc.agendalc.entities.SaludInmunologico;
import com.agendalc.agendalc.entities.SaludMotriz;
import com.agendalc.agendalc.entities.SaludNeurologico;
import com.agendalc.agendalc.entities.SaludOftalmologico;
import com.agendalc.agendalc.entities.SaludOtorrino;
import com.agendalc.agendalc.entities.SaludOtros;
import com.agendalc.agendalc.entities.SaludEstudios;
import com.agendalc.agendalc.entities.SaludProfesion;
import com.agendalc.agendalc.entities.SaludJornada;
import com.agendalc.agendalc.entities.SaludLicenciasOtorgadas;
import com.agendalc.agendalc.entities.SaludMedicamentos;
import com.agendalc.agendalc.entities.SaludEndocrino;
import com.agendalc.agendalc.entities.SaludOncologico;
import com.agendalc.agendalc.entities.SaludRespiratorio;
import com.agendalc.agendalc.entities.SaludPersonales;
import com.agendalc.agendalc.repositories.SaludFormularioRepository;
import com.agendalc.agendalc.services.interfaces.SaludService;

@Service
public class SaludServiceImpl implements SaludService {

    private final SaludFormularioRepository formularioRepository;

    public SaludServiceImpl(SaludFormularioRepository formularioRepository) {
        this.formularioRepository = formularioRepository;
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
        SaludFormulario f = new SaludFormulario();
        f.setRut(dto.getRut());
        f.setFechaFormulario(dto.getFechaFormulario());

        // Llamar a mappers por responsabilidad
        mapPersonales(dto, f);
        mapLicencias(dto, f);
        mapEstudios(dto, f);
        mapProfesion(dto, f);
        mapJornada(dto, f);
        mapCardio(dto, f);
        mapOftalmologico(dto, f);
        mapOtorrino(dto, f);
        mapNeurologico(dto, f);
        mapMotriz(dto, f);
        mapRespiratorio(dto, f);
        mapEndocrino(dto, f);
        mapOncologico(dto, f);
        mapInmunologico(dto, f);
        mapOtros(dto, f);
        mapMedicamentos(dto, f);
        mapConduccion(dto, f);

        return f;
    }

    private void mapPersonales(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getPersonales() == null)
            return;
        SaludPersonales p = new SaludPersonales();
        p.setEdad(dto.getPersonales().getEdad());
        p.setAltura(dto.getPersonales().getAltura());
        p.setPeso(dto.getPersonales().getPeso());
        try {
            if (dto.getPersonales().getGenero() != null)
                p.setGenero(SaludPersonales.Genero.valueOf(dto.getPersonales().getGenero().name()));
        } catch (Exception ex) {
            // invalid genero -> ignore
        }
        p.setFormulario(f);
        f.setPersonales(p);
    }

    private void mapLicencias(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getLicenciasOtorgadas() == null || dto.getLicenciasOtorgadas().isEmpty())
            return;
        List<SaludLicenciasOtorgadas> licencias = new ArrayList<>();
        for (com.agendalc.agendalc.dto.SaludLicenciasDto licDto : dto.getLicenciasOtorgadas()) {
            SaludLicenciasOtorgadas l = new SaludLicenciasOtorgadas();
            if (licDto.getTipoLicencia() != null) {
                l.setTiposLicencias(licDto.getTipoLicencia());
            }
            l.setProfesional(licDto.isProfesional());
            l.setOtras(licDto.isOtras());
            l.setLeyAntigua(licDto.isLeyAntigua());
            l.setNoProfesional(licDto.isNoProfesional());
            l.setEspecial(licDto.isEspecial());
            l.setFormulario(f);
            licencias.add(l);
        }
        f.setLicenciasOtorgadas(licencias);
    }

    private void mapEstudios(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getEstudios() == null)
            return;
        SaludEstudios e = new SaludEstudios();
        try {
            if (dto.getEstudios().getNivelEstudio() != null) {
                e.setNivelEstudio(SaludEstudios.NivelEstudio.valueOf(dto.getEstudios().getNivelEstudio().name()));
            }
        } catch (Exception ex) {
            // ignore invalid enum values
        }
        e.setFormulario(f);
        f.setEstudios(e);
    }

    private void mapProfesion(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getProfesion() == null)
            return;
        SaludProfesion pr = new SaludProfesion();
        pr.setNombre(dto.getProfesion().getNombre());
        pr.setFormulario(f);
        f.setProfesion(pr);
    }

    private void mapJornada(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getJornada() == null)
            return;
        SaludJornada j = new SaludJornada();
        try {
            if (dto.getJornada().getTipoJornada() != null) {
                j.setTipoJornada(SaludJornada.TipoJornada.valueOf(dto.getJornada().getTipoJornada().name()));
            }
            if (dto.getJornada().getSubtipoJornada() != null) {
                j.setSubtipoJornada(SaludJornada.SubtipoJornada.valueOf(dto.getJornada().getSubtipoJornada().name()));
            }
        } catch (Exception ex) {
            // ignore invalid enum values
        }
        j.setFormulario(f);
        f.setJornada(j);
    }

    private void mapCardio(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getCardio() == null)
            return;
        SaludCardio c = new SaludCardio();
        c.setMarcapasos(dto.getCardio().isMarcapasos());
        c.setBypass(dto.getCardio().isBypass());
        c.setInsuficienciaCardiaca(dto.getCardio().isInsuficienciaCardiaca());
        c.setAnginas(dto.getCardio().isAnginas());
        c.setPalpitaciones(dto.getCardio().isPalpitaciones());
        c.setValvula(dto.getCardio().isValvula());
        c.setHipertension(dto.getCardio().isHipertension());
        c.setInfarto(dto.getCardio().isInfarto());
        c.setArritmias(dto.getCardio().isArritmias());
        c.setDolorPecho(dto.getCardio().isDolorPecho());
        c.setFormulario(f);
        f.setCardio(c);
    }

    private void mapOftalmologico(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getOftalmologico() == null)
            return;
        SaludOftalmologico o = new SaludOftalmologico();

        if (dto.getOftalmologico().getCataratas() != null) {
            o.setCataratasOjoIzquierdo(dto.getOftalmologico().getCataratas().isOjoIzquierdo());
            o.setCataratasOjoDerecho(dto.getOftalmologico().getCataratas().isOjoDerecho());
        }

        if (dto.getOftalmologico().getGlaucoma() != null) {
            o.setGlaucomaOjoIzquierdo(dto.getOftalmologico().getGlaucoma().isOjoIzquierdo());
            o.setGlaucomaOjoDerecho(dto.getOftalmologico().getGlaucoma().isOjoDerecho());
        }

        if (dto.getOftalmologico().getRetina() != null) {
            o.setRetinaOjoIzquierdo(dto.getOftalmologico().getRetina().isOjoIzquierdo());
            o.setRetinaOjoDerecho(dto.getOftalmologico().getRetina().isOjoDerecho());
        }

        o.setUsaLentes(dto.getOftalmologico().isLentesCerca() || dto.getOftalmologico().isLentesLejos());
        if (dto.getOftalmologico().isLentesCerca() && dto.getOftalmologico().isLentesLejos()) {
            o.setTipoLente("Ambos");
        } else if (dto.getOftalmologico().isLentesCerca()) {
            o.setTipoLente("Cerca");
        } else if (dto.getOftalmologico().isLentesLejos()) {
            o.setTipoLente("Lejos");
        } else {
            o.setTipoLente("Ninguno");
        }

        o.setOperacionOjos(dto.getOftalmologico().isOperacionOjos());
        o.setFormulario(f);
        f.setOftalmologico(o);
    }

    private void mapOtorrino(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getOtorrino() == null)
            return;
        SaludOtorrino o = new SaludOtorrino();
        o.setUsarAudifonos(dto.getOtorrino().isUsarAudifonos());
        o.setOperacionOido(dto.getOtorrino().isOperacionOido());
        o.setMareos(dto.getOtorrino().isMareos());
        o.setFormulario(f);
        f.setOtorrino(o);
    }

    private void mapNeurologico(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getNeurologico() == null)
            return;
        SaludNeurologico n = new SaludNeurologico();
        n.setDesmayo(dto.getNeurologico().isDesmayo());
        n.setEsclerosisMultiple(dto.getNeurologico().isEsclerosisMultiple());
        n.setDificultadHablar(dto.getNeurologico().isDificultadHablar());
        n.setPsiquiatrico(dto.getNeurologico().isPsiquiatrico());
        n.setDerrameCerebral(dto.getNeurologico().isDerrameCerebral());
        n.setEpilepsia(dto.getNeurologico().isEpilepsia());
        n.setParkinson(dto.getNeurologico().isParkinson());
        n.setOlvido(dto.getNeurologico().isOlvido());
        n.setEmocional(dto.getNeurologico().isEmocional());
        n.setFormulario(f);
        f.setNeurologico(n);
    }

    private void mapMotriz(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getMotriz() == null)
            return;
        SaludMotriz m = new SaludMotriz();
        m.setPerdidaFuerza(dto.getMotriz().isPerdidaFuerza());
        m.setPerdidaExtremidades(dto.getMotriz().isPerdidaExtremidades());
        m.setFormulario(f);
        f.setMotriz(m);
    }

    private void mapRespiratorio(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getRespiratorio() == null)
            return;
        SaludRespiratorio r = new SaludRespiratorio();
        r.setDificultadRespirar(dto.getRespiratorio().isDificultadRespirar());
        r.setProblemasDormir(dto.getRespiratorio().isProblemasDormir());
        r.setFatiga(dto.getRespiratorio().isFatiga());
        r.setRonquidos(dto.getRespiratorio().isRonquidos());
        r.setFormulario(f);
        f.setRespiratorio(r);
    }

    private void mapEndocrino(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getEndocrino() == null)
            return;
        SaludEndocrino e = new SaludEndocrino();
        e.setDiabetes(dto.getEndocrino().isDiabetes());
        e.setFormulario(f);
        f.setEndocrino(e);
    }

    private void mapOncologico(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getOncologico() == null)
            return;
        SaludOncologico o = new SaludOncologico();
        o.setSistemaNervioso(dto.getOncologico().isSistemaNervioso());
        o.setCabezaCuello(dto.getOncologico().isCabezaCuello());
        o.setTorax(dto.getOncologico().isTorax());
        o.setEndocrino(dto.getOncologico().isEndocrino());
        o.setReproductorMasculino(dto.getOncologico().isReproductorMasculino());
        o.setReproductorFemenino(dto.getOncologico().isReproductorFemenino());
        o.setOseoMuscular(dto.getOncologico().isOseoMuscular());
        o.setPiel(dto.getOncologico().isPiel());
        o.setOjos(dto.getOncologico().isOjos());
        o.setSistemaRespiratorio(dto.getOncologico().isSistemaRespiratorio());
        o.setSistemaDigestivo(dto.getOncologico().isSistemaDigestivo());
        o.setSistemaGenitourinario(dto.getOncologico().isSistemaGenitourinario());
        o.setHematologico(dto.getOncologico().isHematologico());
        o.setOtros(dto.getOncologico().isOtros());
        o.setFormulario(f);
        f.setOncologico(o);
    }

    private void mapInmunologico(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getInmunologico() == null)
            return;
        SaludInmunologico i = new SaludInmunologico();
        i.setHigado(dto.getInmunologico().isHigado());
        i.setRenal(dto.getInmunologico().isRenal());
        i.setFormulario(f);
        f.setInmunologico(i);
    }

    private void mapOtros(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getOtros() == null)
            return;
        SaludOtros o = new SaludOtros();

        if (dto.getOtros().getOperaciones() != null) {
            o.setOperado(dto.getOtros().getOperaciones().isOperado());
            o.setDetalleOperado(dto.getOtros().getOperaciones().getDetalle());
        }

        if (dto.getOtros().getEnfermedad() != null) {
            o.setOtraEnfermedad(dto.getOtros().getEnfermedad().isTieneEnfermedad());
            o.setDetalleEnfermedad(dto.getOtros().getEnfermedad().getDetalle());
        }

        if (dto.getOtros().getOtraEnfermedad() != null) {
            o.setLicenciaMedica(dto.getOtros().getOtraEnfermedad().isLicencia());
            o.setDetalleLicenciaMedica(dto.getOtros().getOtraEnfermedad().getDetalle());
        }

        o.setFormulario(f);
        f.setOtros(o);
    }

    private void mapMedicamentos(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getMedicamentos() == null || dto.getMedicamentos().isEmpty())
            return;
        List<SaludMedicamentos> meds = new ArrayList<>();
        for (SaludMedicamentoDto md : dto.getMedicamentos()) {
            SaludMedicamentos m = new SaludMedicamentos();
            m.setNombreMedicamento(md.getNombreMedicamento());
            m.setDosis(md.getDosis());
            m.setPorque(md.getMotivo());
            m.setFormulario(f);
            meds.add(m);
        }
        f.setMedicamentos(meds);
    }

    private void mapConduccion(SaludFormularioDto dto, SaludFormulario f) {
        if (dto.getConduccion() == null)
            return;
        SaludConduccion c = new SaludConduccion();
        SaludConduccionDto cd = dto.getConduccion();
        c.setTodoslosDias(cd.isTodoslosDias());
        c.setAlgunosDiasSemana(cd.isAlgunosDiasSemana());
        c.setAlgunosDiasMes(cd.isAlgunosDiasMes());
        c.setAlgunosDiasAnio(cd.isAlgunosDiasAnio());
        c.setUtilizaTrabajar(cd.isUtilizaTrabajar());
        c.setEvalucionesMedicas(cd.isEvalucionesMedicas());
        c.setCiudad(cd.isCiudad());
        c.setCarretera(cd.isCarretera());
        c.setAmbos(cd.isAmbos());
        c.setAccidentes(cd.isAccidentes());
        c.setDetalleAccidentes(cd.getDetalleAccidentes());
        c.setFormulario(f);
        f.setConduccion(c);
    }

    private SaludFormularioDto mapToDto(SaludFormulario f) {
        SaludFormularioDto dto = new SaludFormularioDto();
        dto.setRut(f.getRut());

        dto.setPersonales(toPersonalesDto(f.getPersonales()));
        dto.setLicenciasOtorgadas(toLicenciasDto(f.getLicenciasOtorgadas()));
        dto.setEstudios(toEstudiosDto(f.getEstudios()));
        dto.setProfesion(toProfesionDto(f.getProfesion()));
        dto.setJornada(toJornadaDto(f.getJornada()));
        dto.setCardio(toCardioDto(f.getCardio()));
        dto.setOftalmologico(toOftalmologicoDto(f.getOftalmologico()));
        dto.setOtorrino(toOtorrinoDto(f.getOtorrino()));
        dto.setNeurologico(toNeurologicoDto(f.getNeurologico()));
        dto.setMotriz(toMotrizDto(f.getMotriz()));
        dto.setRespiratorio(toRespiratorioDto(f.getRespiratorio()));
        dto.setEndocrino(toEndocrinoDto(f.getEndocrino()));
        dto.setOncologico(toOncologicoDto(f.getOncologico()));
        dto.setInmunologico(toInmunologicoDto(f.getInmunologico()));
        dto.setOtros(toOtrosDto(f.getOtros()));
        dto.setMedicamentos(toMedicamentosDto(f.getMedicamentos()));
        dto.setConduccion(toConduccionDto(f.getConduccion()));

        return dto;
    }

    private java.util.List<com.agendalc.agendalc.dto.SaludLicenciasDto> toLicenciasDto(
            java.util.List<SaludLicenciasOtorgadas> list) {
        if (list == null || list.isEmpty())
            return new java.util.ArrayList<>();

        java.util.List<com.agendalc.agendalc.dto.SaludLicenciasDto> licenciasDtos = new java.util.ArrayList<>();
        for (SaludLicenciasOtorgadas l : list) {
            com.agendalc.agendalc.dto.SaludLicenciasDto ld = new com.agendalc.agendalc.dto.SaludLicenciasDto();
            if (l.getTiposLicencias() != null) {
                ld.setTipoLicencia(l.getTiposLicencias());
            }
            ld.setProfesional(l.isProfesional());
            ld.setOtras(l.isOtras());
            ld.setLeyAntigua(l.isLeyAntigua());
            ld.setNoProfesional(l.isNoProfesional());
            ld.setEspecial(l.isEspecial());
            licenciasDtos.add(ld);
        }

        return licenciasDtos;
    }

    private com.agendalc.agendalc.dto.SaludEstudiosDto toEstudiosDto(SaludEstudios e) {
        if (e == null)
            return null;
        com.agendalc.agendalc.dto.SaludEstudiosDto ed = new com.agendalc.agendalc.dto.SaludEstudiosDto();
        if (e.getNivelEstudio() != null) {
            try {
                ed.setNivelEstudio(
                        com.agendalc.agendalc.dto.SaludEstudiosDto.NivelEstudio.valueOf(e.getNivelEstudio().name()));
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        return ed;
    }

    private com.agendalc.agendalc.dto.SaludProfesionDto toProfesionDto(SaludProfesion p) {
        if (p == null)
            return null;
        com.agendalc.agendalc.dto.SaludProfesionDto pd = new com.agendalc.agendalc.dto.SaludProfesionDto();
        pd.setNombre(p.getNombre());
        return pd;
    }

    private com.agendalc.agendalc.dto.SaludJornadaDto toJornadaDto(SaludJornada j) {
        if (j == null)
            return null;
        com.agendalc.agendalc.dto.SaludJornadaDto jd = new com.agendalc.agendalc.dto.SaludJornadaDto();
        if (j.getTipoJornada() != null) {
            try {
                jd.setTipoJornada(
                        com.agendalc.agendalc.dto.SaludJornadaDto.TipoJornada.valueOf(j.getTipoJornada().name()));
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        if (j.getSubtipoJornada() != null) {
            try {
                jd.setSubtipoJornada(com.agendalc.agendalc.dto.SaludJornadaDto.SubtipoJornada
                        .valueOf(j.getSubtipoJornada().name()));
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        return jd;
    }

    private com.agendalc.agendalc.dto.SaludOtorrinoDto toOtorrinoDto(SaludOtorrino o) {
        if (o == null)
            return null;
        com.agendalc.agendalc.dto.SaludOtorrinoDto od = new com.agendalc.agendalc.dto.SaludOtorrinoDto();
        od.setUsarAudifonos(o.isUsarAudifonos());
        od.setOperacionOido(o.isOperacionOido());
        od.setMareos(o.isMareos());
        return od;
    }

    private com.agendalc.agendalc.dto.SaludEndocrinoDto toEndocrinoDto(SaludEndocrino e) {
        if (e == null)
            return null;
        com.agendalc.agendalc.dto.SaludEndocrinoDto ed = new com.agendalc.agendalc.dto.SaludEndocrinoDto();
        ed.setDiabetes(e.isDiabetes());
        return ed;
    }

    private com.agendalc.agendalc.dto.SaludOncologicoDto toOncologicoDto(SaludOncologico o) {
        if (o == null)
            return null;
        com.agendalc.agendalc.dto.SaludOncologicoDto od = new com.agendalc.agendalc.dto.SaludOncologicoDto();
        od.setSistemaNervioso(o.isSistemaNervioso());
        od.setCabezaCuello(o.isCabezaCuello());
        od.setTorax(o.isTorax());
        od.setEndocrino(o.isEndocrino());
        od.setReproductorMasculino(o.isReproductorMasculino());
        od.setReproductorFemenino(o.isReproductorFemenino());
        od.setOseoMuscular(o.isOseoMuscular());
        od.setPiel(o.isPiel());
        od.setOjos(o.isOjos());
        od.setSistemaRespiratorio(o.isSistemaRespiratorio());
        od.setSistemaDigestivo(o.isSistemaDigestivo());
        od.setSistemaGenitourinario(o.isSistemaGenitourinario());
        od.setHematologico(o.isHematologico());
        od.setOtros(o.isOtros());
        return od;
    }

    private com.agendalc.agendalc.dto.SaludInmunologicoDto toInmunologicoDto(SaludInmunologico i) {
        if (i == null)
            return null;
        com.agendalc.agendalc.dto.SaludInmunologicoDto id = new com.agendalc.agendalc.dto.SaludInmunologicoDto();
        id.setHigado(i.isHigado());
        id.setRenal(i.isRenal());
        return id;
    }


    private com.agendalc.agendalc.dto.SaludPersonalesDto toPersonalesDto(SaludPersonales p) {
        if (p == null)
            return null;
        com.agendalc.agendalc.dto.SaludPersonalesDto pd = new com.agendalc.agendalc.dto.SaludPersonalesDto();
        pd.setEdad(p.getEdad());
        pd.setAltura(p.getAltura());
        pd.setPeso(p.getPeso());
        if (p.getGenero() != null) {
            try {
                pd.setGenero(com.agendalc.agendalc.dto.SaludPersonalesDto.Genero.valueOf(p.getGenero().name()));
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        return pd;
    }

    private com.agendalc.agendalc.dto.SaludCardioDto toCardioDto(SaludCardio c) {
        if (c == null)
            return null;
        com.agendalc.agendalc.dto.SaludCardioDto cd = new com.agendalc.agendalc.dto.SaludCardioDto();
        cd.setMarcapasos(c.isMarcapasos());
        cd.setBypass(c.isBypass());
        cd.setInsuficienciaCardiaca(c.isInsuficienciaCardiaca());
        cd.setAnginas(c.isAnginas());
        cd.setPalpitaciones(c.isPalpitaciones());
        cd.setValvula(c.isValvula());
        cd.setHipertension(c.isHipertension());
        cd.setInfarto(c.isInfarto());
        cd.setArritmias(c.isArritmias());
        cd.setDolorPecho(c.isDolorPecho());
        return cd;
    }

    private com.agendalc.agendalc.dto.SaludOftalmologicoDto toOftalmologicoDto(SaludOftalmologico o) {
        if (o == null)
            return null;
        com.agendalc.agendalc.dto.SaludOftalmologicoDto od = new com.agendalc.agendalc.dto.SaludOftalmologicoDto();

        com.agendalc.agendalc.dto.CataratasDto cataratasDto = new com.agendalc.agendalc.dto.CataratasDto();
        cataratasDto.setOjoIzquierdo(o.isCataratasOjoIzquierdo());
        cataratasDto.setOjoDerecho(o.isCataratasOjoDerecho());
        od.setCataratas(cataratasDto);

        com.agendalc.agendalc.dto.GlaucomaDto glaucomaDto = new com.agendalc.agendalc.dto.GlaucomaDto();
        glaucomaDto.setOjoIzquierdo(o.isGlaucomaOjoIzquierdo());
        glaucomaDto.setOjoDerecho(o.isGlaucomaOjoDerecho());
        od.setGlaucoma(glaucomaDto);

        com.agendalc.agendalc.dto.RetinaDto retinaDto = new com.agendalc.agendalc.dto.RetinaDto();
        retinaDto.setOjoIzquierdo(o.isRetinaOjoIzquierdo());
        retinaDto.setOjoDerecho(o.isRetinaOjoDerecho());
        od.setRetina(retinaDto);

        if (o.getTipoLente() != null) {
            if (o.getTipoLente().equals("Ambos")) {
                od.setLentesCerca(true);
                od.setLentesLejos(true);
            } else if (o.getTipoLente().equals("Cerca")) {
                od.setLentesCerca(true);
            } else if (o.getTipoLente().equals("Lejos")) {
                od.setLentesLejos(true);
            }
        }

        od.setOperacionOjos(o.isOperacionOjos());
        return od;
    }

    private com.agendalc.agendalc.dto.SaludNeurologicoDto toNeurologicoDto(SaludNeurologico n) {
        if (n == null)
            return null;
        com.agendalc.agendalc.dto.SaludNeurologicoDto nd = new com.agendalc.agendalc.dto.SaludNeurologicoDto();
        nd.setDesmayo(n.isDesmayo());
        nd.setEsclerosisMultiple(n.isEsclerosisMultiple());
        nd.setDificultadHablar(n.isDificultadHablar());
        nd.setPsiquiatrico(n.isPsiquiatrico());
        nd.setDerrameCerebral(n.isDerrameCerebral());
        nd.setEpilepsia(n.isEpilepsia());
        nd.setParkinson(n.isParkinson());
        nd.setOlvido(n.isOlvido());
        nd.setEmocional(n.isEmocional());
        return nd;
    }

    private com.agendalc.agendalc.dto.SaludMotrizDto toMotrizDto(SaludMotriz m) {
        if (m == null)
            return null;
        com.agendalc.agendalc.dto.SaludMotrizDto md = new com.agendalc.agendalc.dto.SaludMotrizDto();
        md.setPerdidaFuerza(m.isPerdidaFuerza());
        md.setPerdidaExtremidades(m.isPerdidaExtremidades());
        return md;
    }

    private com.agendalc.agendalc.dto.SaludRespiratorioDto toRespiratorioDto(SaludRespiratorio r) {
        if (r == null)
            return null;
        com.agendalc.agendalc.dto.SaludRespiratorioDto rd = new com.agendalc.agendalc.dto.SaludRespiratorioDto();
        rd.setDificultadRespirar(r.isDificultadRespirar());
        rd.setProblemasDormir(r.isProblemasDormir());
        rd.setFatiga(r.isFatiga());
        rd.setRonquidos(r.isRonquidos());
        return rd;
    }

    private java.util.List<com.agendalc.agendalc.dto.SaludMedicamentoDto> toMedicamentosDto(
            java.util.List<SaludMedicamentos> list) {
        if (list == null || list.isEmpty())
            return new java.util.ArrayList<>();
        java.util.List<com.agendalc.agendalc.dto.SaludMedicamentoDto> meds = new java.util.ArrayList<>();
        for (SaludMedicamentos m : list) {
            com.agendalc.agendalc.dto.SaludMedicamentoDto md = new com.agendalc.agendalc.dto.SaludMedicamentoDto();
            md.setNombreMedicamento(m.getNombreMedicamento());
            md.setDosis(m.getDosis());
            md.setMotivo(m.getPorque());
            meds.add(md);
        }
        return meds;
    }

    private com.agendalc.agendalc.dto.SaludConduccionDto toConduccionDto(SaludConduccion c) {
        if (c == null)
            return null;
        com.agendalc.agendalc.dto.SaludConduccionDto cd = new com.agendalc.agendalc.dto.SaludConduccionDto();
        cd.setTodoslosDias(c.isTodoslosDias());
        cd.setAlgunosDiasSemana(c.isAlgunosDiasSemana());
        cd.setAlgunosDiasMes(c.isAlgunosDiasMes());
        cd.setAlgunosDiasAnio(c.isAlgunosDiasAnio());
        cd.setUtilizaTrabajar(c.isUtilizaTrabajar());
        cd.setEvalucionesMedicas(c.isEvalucionesMedicas());
        cd.setCiudad(c.isCiudad());
        cd.setCarretera(c.isCarretera());
        cd.setAmbos(c.isAmbos());
        cd.setAccidentes(c.isAccidentes());
        cd.setDetalleAccidentes(c.getDetalleAccidentes());
        return cd;
    }

    private com.agendalc.agendalc.dto.SaludOtrosDto toOtrosDto(SaludOtros o) {
        if (o == null)
            return null;
        com.agendalc.agendalc.dto.SaludOtrosDto od = new com.agendalc.agendalc.dto.SaludOtrosDto();

        com.agendalc.agendalc.dto.SaludOtrosDto.OperadoDto opDto = new com.agendalc.agendalc.dto.SaludOtrosDto.OperadoDto();
        opDto.setOperado(o.isOperado());
        opDto.setDetalle(o.getDetalleOperado());
        od.setOperaciones(opDto);

        com.agendalc.agendalc.dto.SaludOtrosDto.EnfermedadDto enfDto = new com.agendalc.agendalc.dto.SaludOtrosDto.EnfermedadDto();
        enfDto.setTieneEnfermedad(o.isOtraEnfermedad());
        enfDto.setDetalle(o.getDetalleEnfermedad());
        od.setEnfermedad(enfDto);

        com.agendalc.agendalc.dto.SaludOtrosDto.LicenciaMedicaDto licDto = new com.agendalc.agendalc.dto.SaludOtrosDto.LicenciaMedicaDto();
        licDto.setLicencia(o.isLicenciaMedica());
        licDto.setDetalle(o.getDetalleLicenciaMedica());
        od.setOtraEnfermedad(licDto);

        return od;
    }
}
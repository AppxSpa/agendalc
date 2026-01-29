package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.entities.AsistenciaCita;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.repositories.AsistenciaRepository;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.services.interfaces.AsistenciaCitaService;
import com.agendalc.agendalc.utils.RelojUtils;

@Service
public class AsistenciaCitaServiceImpl implements AsistenciaCitaService {

    private final CitaRepository citaRepository;
    private final AsistenciaRepository asistenciaCitaRepository;

    public AsistenciaCitaServiceImpl(CitaRepository citaRepository, AsistenciaRepository asistenciaCitaRepository) {
        this.citaRepository = citaRepository;
        this.asistenciaCitaRepository = asistenciaCitaRepository;
    }

    @Override
    @Transactional
    public AsistenciaCita registrarAsistencia(Long citaId) {

        // 1. Buscar la Cita por citaId. Si no existe, lanzar excepción.
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + citaId));
        // 2. Verificar si ya existe una asistencia para esa cita. Si existe, lanzar
        // excepción o manejar el caso.
        if (asistenciaCitaRepository.existsByCitaIdCita(citaId)) {
            throw new IllegalStateException("La asistencia ya ha sido registrada para la cita con ID: " + citaId);
        }
        // 3. Crear una nueva instancia de AsistenciaCita.
        AsistenciaCita asistenciaCita = new AsistenciaCita();
        asistenciaCita.setCita(cita);
        asistenciaCita.setFechaHoraAsistencia(RelojUtils.obtenerFechaHoraActual());

        return asistenciaCitaRepository.save(asistenciaCita);

    }
}

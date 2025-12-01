package com.agendalc.agendalc.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class RelojUtils {

    private RelojUtils() {
        throw new IllegalStateException("Clase de utilidades no instanciable");
    }

    public static LocalDateTime obtenerFechaHoraActual() {
        return LocalDateTime.now(zonaSantiago());
    }

    /**
     * Devuelve la fecha actual (solo fecha) en la zona horaria de Santiago, Chile.
     */
    public static LocalDate obtenerFechaActual() {
        return LocalDate.now(zonaSantiago());
    }

    /**
     * Devuelve la ZoneId para la zona horaria de Santiago, Chile.
     */
    public static ZoneId zonaSantiago() {
        return ZoneId.of("America/Santiago");
    }

}

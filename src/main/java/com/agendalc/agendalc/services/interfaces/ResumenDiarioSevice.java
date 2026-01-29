package com.agendalc.agendalc.services.interfaces;

import java.time.LocalDate;

import com.agendalc.agendalc.dto.ResumenDarioResponse;

public interface ResumenDiarioSevice {

    ResumenDarioResponse resumendiario(LocalDate fecha);

}

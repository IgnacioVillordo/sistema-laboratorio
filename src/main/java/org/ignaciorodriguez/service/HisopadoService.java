package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.Hisopado;

import java.util.List;
import java.util.Optional;

public interface HisopadoService {
    Hisopado guardarHisopado(Hisopado hisopado);
    Hisopado editarHisopado(Hisopado hisopado, Long id);
    Optional<Hisopado> recuperarHisopado(Long id);
    List<Hisopado> recuperarHisopados();
    Boolean cambiarTipoHisopado(Long id);
}

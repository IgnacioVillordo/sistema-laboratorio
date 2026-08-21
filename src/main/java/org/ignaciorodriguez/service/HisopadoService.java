package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.Hisopado;
import org.ignaciorodriguez.modelo.VistaHisopado;

import java.util.List;
import java.util.Optional;

public interface HisopadoService {
    Hisopado guardarHisopado(Hisopado hisopado);
    Hisopado editarHisopado(Hisopado hisopado, Long id);
    Optional<VistaHisopado> recuperarHisopado(Long id);
    List<VistaHisopado> recuperarHisopados();
    Boolean cambiarTipoHisopado(Long id);
}

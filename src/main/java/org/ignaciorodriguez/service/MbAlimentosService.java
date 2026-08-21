package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.MbAlimentos;
import org.ignaciorodriguez.modelo.VistaMbAlimentos;

import java.util.List;
import java.util.Optional;

public interface MbAlimentosService {
    MbAlimentos guardarMbAlimentos(MbAlimentos mbAlimentos);
    MbAlimentos editarMbAlimentos(MbAlimentos mbAlimentos, Long id);
    Optional<VistaMbAlimentos> recuperarMbAlimento(Long id);
    List<VistaMbAlimentos> recuperarMbAlimentos();
}

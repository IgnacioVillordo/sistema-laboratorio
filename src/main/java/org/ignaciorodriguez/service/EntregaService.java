package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.Entrega;
import org.ignaciorodriguez.modelo.VistaEntregas;

import java.util.List;

public interface EntregaService {

    Entrega guardarEntrega(Entrega entrega);
    Entrega borrarEntrega(Long id);
    Boolean hayEntrega(Long id);
    List<String> recuperarProcedencias();
    List<VistaEntregas> recuperarVistaEntregas();
}

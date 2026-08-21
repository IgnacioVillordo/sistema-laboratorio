package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.Manual;

import java.util.List;
import java.util.Optional;

public interface ManualService {

    Manual guardarManual(Manual manual);
    Manual editarManual(Manual manual, Long id);
    Optional<Manual> recuperarManual(Long id);
    List<Manual> recuperarManuales();
    Optional<String> recuperarTitulo(Long id);
    Manual guardarMostrar(Long id, Boolean mostrar);
    Optional<Boolean> recuperarMostrar(Long id);
    Boolean existeManual(Long id);
}

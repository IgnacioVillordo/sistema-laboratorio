package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.MbAgua;
import org.ignaciorodriguez.modelo.VistaMbAgua;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MbAguaService {

    MbAgua guardarPhYCloro(Double ph, Double cloroLibre, Double cloroTotal, Long id);
    MbAgua guardarMbAgua(MbAgua mbAgua);
    MbAgua editarMbAgua(MbAgua mbAgua, Long id);
    Optional<VistaMbAgua> recuperarMbAgua(Long id);
    List<VistaMbAgua> recuperarMbAguas();
    MbAgua guardarFechaAnalisis(LocalDate fechaAnalisis, Long id);
    MbAgua guardarLimitesMohos(Boolean mohosLimite, Long id);
    boolean existeMbAgua(Long id);
}

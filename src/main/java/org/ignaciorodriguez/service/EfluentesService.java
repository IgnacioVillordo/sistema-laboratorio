package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.Efluentes;

import java.util.Optional;

public interface EfluentesService {
    Efluentes guardarEfluente(Efluentes efluente);
    Efluentes editarEfluente(Efluentes efluente, Long id);
    Optional<Efluentes> recuperarEfluente(Long id);
}

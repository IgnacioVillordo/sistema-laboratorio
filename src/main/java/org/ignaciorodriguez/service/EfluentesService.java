package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.Efluente;

import java.util.Optional;

public interface EfluentesService {
    Efluente guardarEfluente(Efluente efluente);
    Efluente editarEfluente(Efluente efluente, Long id);
    Optional<Efluente> recuperarEfluente(Long id);
}

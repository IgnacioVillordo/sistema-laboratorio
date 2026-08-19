package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.Efluente;
import org.ignaciorodriguez.repository.EfluentesRepository;
import org.ignaciorodriguez.service.EfluentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EfluentesServiceImpl implements EfluentesService {

    @Autowired
    EfluentesRepository efluentesRepository;

    @Override
    public Efluente guardarEfluente(Efluente efluente) {
        return efluentesRepository.save(efluente);
    }

    @Override
    public Efluente editarEfluente(Efluente efluente, Long id) {
        return efluentesRepository.findById(id)
                .map(efluenteDb -> {
                    efluente.setIdmuestras(id);
                    return efluentesRepository.save(efluente);
                })
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el efluente"));
    }

    @Override
    public Optional<Efluente> recuperarEfluente(Long id) {
        return efluentesRepository.findById(id);
    }
}

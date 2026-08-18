package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.Efluentes;
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
    public Efluentes guardarEfluente(Efluentes efluente) {
        return efluentesRepository.save(efluente);
    }

    @Override
    public Efluentes editarEfluente(Efluentes efluente, Long id) {
        return efluentesRepository.findById(id)
                .map(efluenteDb -> {
                    efluente.setIdefluentes(id);
                    return efluentesRepository.save(efluenteDb);
                })
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el efluente"));
    }

    @Override
    public Optional<Efluentes> recuperarEfluente(Long id) {
        return efluentesRepository.findById(id);
    }
}

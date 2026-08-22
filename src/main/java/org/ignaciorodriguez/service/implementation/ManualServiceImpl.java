package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.Manual;
import org.ignaciorodriguez.repository.ManualRepository;
import org.ignaciorodriguez.service.ManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManualServiceImpl implements ManualService {

    @Autowired
    ManualRepository manualRepository;

    @Override
    public Manual guardarManual(Manual manual) {
        return manualRepository.save(manual);
    }

    @Override
    public Manual editarManual(Manual manual, Long id) {
        return manualRepository.findById(id).map(manualDb -> {
            manual.setIdmuestras(id);
            return manualRepository.save(manual);
        }).orElseThrow(() -> new EntityNotFoundException("Analisis no encontrado"));
    }

    @Override
    public Optional<Manual> recuperarManual(Long id) {
        return manualRepository.findById(id);
    }

    @Override
    public List<Manual> recuperarManuales() {
        return manualRepository.findAll();
    }

    @Override
    public Optional<String> recuperarTitulo(Long id) {
        return manualRepository.findTituloByIdmuestras(id);
    }

    @Override
    public Manual guardarMostrar(Long id, Boolean mostrar) {
        return manualRepository.findById(id)
                .map(manual -> {
                    manual.setMostrar(mostrar);
                    return manualRepository.save(manual);
                })
                .orElseThrow(() -> new EntityNotFoundException("Manual no encontrado"));
    }

    @Override
    public Optional<Boolean> recuperarMostrar(Long id) {
        return manualRepository.findMostrarByIdmuestras(id);
    }

    @Override
    public Boolean existeManual(Long id) {
        return manualRepository.existsById(id);
    }
}

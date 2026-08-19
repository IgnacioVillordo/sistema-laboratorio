package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.FqAgua;
import org.ignaciorodriguez.repository.FqAguaRepository;
import org.ignaciorodriguez.service.FqAguaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FqAguaServiceImpl implements FqAguaService {
    @Autowired
    FqAguaRepository fqAguaRepository;


    @Override
    public FqAgua guardarFqAgua(FqAgua fqAgua) {
        return fqAguaRepository.save(fqAgua);
    }

    @Override
    public FqAgua editarFqAgua(FqAgua fqAgua, Long id) {
        return fqAguaRepository.findById(id).map(fqAguaDb -> {
            fqAgua.setIdmuestras(id);
            return fqAguaRepository.save(fqAgua);
        }).orElseThrow(() -> new EntityNotFoundException("Analisis Fq Agua no encontrado."));
    }

    @Override
    public Optional<FqAgua> recuperarFqAgua(Long id) {
        return fqAguaRepository.findById(id);
    }

    @Override
    public List<FqAgua> recuperarFqAguas() {
        return fqAguaRepository.findAll();
    }
}

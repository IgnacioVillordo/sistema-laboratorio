package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.MbAlimentos;
import org.ignaciorodriguez.modelo.VistaMbAlimentos;
import org.ignaciorodriguez.repository.MbAlimentosRepository;
import org.ignaciorodriguez.repository.VistaMbAlimentosRepository;
import org.ignaciorodriguez.service.MbAlimentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MbAlimentosServiceImpl implements MbAlimentosService {

    @Autowired
    MbAlimentosRepository mbAlimentosRepository;
    @Autowired
    VistaMbAlimentosRepository vistaMbAlimentosRepository;

    @Override
    public MbAlimentos guardarMbAlimentos(MbAlimentos mbAlimentos) {
        return mbAlimentosRepository.save(mbAlimentos);
    }

    @Override
    public MbAlimentos editarMbAlimentos(MbAlimentos mbAlimentos, Long id) {
        return mbAlimentosRepository.findById(id).map(mbAlimentosDb -> {
            mbAlimentos.setIdmuestras(id);
            return mbAlimentosRepository.save(mbAlimentos);
        }).orElseThrow(() -> new EntityNotFoundException("Analisis no encontrado"));
    }

    @Override
    public Optional<VistaMbAlimentos> recuperarMbAlimento(Long id) {
        return vistaMbAlimentosRepository.findById(id);
    }

    @Override
    public List<VistaMbAlimentos> recuperarMbAlimentos() {
        return vistaMbAlimentosRepository.findAll();
    }
}

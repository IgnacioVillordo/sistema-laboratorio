package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.Entrega;
import org.ignaciorodriguez.modelo.VistaEntregas;
import org.ignaciorodriguez.repository.EntregaRepository;
import org.ignaciorodriguez.repository.VistaEntregasRepository;
import org.ignaciorodriguez.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EntregaServiceImpl implements EntregaService {

    @Autowired
    EntregaRepository entregaRepository;
    @Autowired
    VistaEntregasRepository vistaEntregasRepository;

    @Override
    public Entrega guardarEntrega(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    @Override
    public Entrega borrarEntrega(Long id) {
        return entregaRepository.findById(id).map(entrega -> {
            entrega.setActivo(false);
            return entregaRepository.save(entrega);
        }).orElseThrow(() -> new EntityNotFoundException("Entrega no encontrada"));
    }

    @Override
    public Boolean hayEntrega(Long id) {
        return entregaRepository.existsById(id);
    }

    @Override
    public List<String> recuperarProcedencias() {
        return vistaEntregasRepository.findDistinctProcedenciaBy();
    }

    @Override
    public List<VistaEntregas> recuperarVistaEntregas() {
        return vistaEntregasRepository.findAll();
    }
}

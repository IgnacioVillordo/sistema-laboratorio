package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.Hisopado;
import org.ignaciorodriguez.repository.HisopadoRepository;
import org.ignaciorodriguez.service.HisopadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HisopadoServiceImpl implements HisopadoService {

    @Autowired
    HisopadoRepository hisopadoRepository;

    @Override
    public Hisopado guardarHisopado(Hisopado hisopado) {
        return hisopadoRepository.save(hisopado);
    }

    @Override
    public Hisopado editarHisopado(Hisopado hisopado, Long id) {
        return hisopadoRepository.findById(id).map(hisopadoBd -> {
            hisopado.setIdmuestras(id);
            return hisopadoRepository.save(hisopado);
        }).orElseThrow(() -> new EntityNotFoundException("Hisopado no encontrado"));
    }

    @Override
    public Optional<Hisopado> recuperarHisopado(Long id) {
        return hisopadoRepository.findById(id);
    }

    @Override
    public List<Hisopado> recuperarHisopados() {
        return hisopadoRepository.findAll();
    }

    @Override
    public Boolean cambiarTipoHisopado(Long id) {
        return hisopadoRepository.findById(id).map(hisopado -> {
            hisopado.setGermenesPotencia("0");
            hisopado.setTotalesPotencia("0");
            hisopado.setStaphilococosPotencia("0");
            hisopado.setEnterobacterias("0");
            hisopadoRepository.save(hisopado);
            return true;
        }).orElse(false);
    }
}

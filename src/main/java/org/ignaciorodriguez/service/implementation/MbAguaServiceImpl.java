package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.MbAgua;
import org.ignaciorodriguez.modelo.VistaMbAgua;
import org.ignaciorodriguez.repository.MbAguaRepository;
import org.ignaciorodriguez.repository.VistaMbAguaRepository;
import org.ignaciorodriguez.service.MbAguaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MbAguaServiceImpl implements MbAguaService {

    @Autowired
    MbAguaRepository mbAguaRepository;
    @Autowired
    VistaMbAguaRepository vistaMbAguaRepository;

    @Override
    public MbAgua guardarPhYCloro(Double ph, Double cloroLibre, Double cloroTotal, Long id) {
        MbAgua mbAgua = mbAguaRepository.findById(id).orElse(new MbAgua());
        mbAgua.setIdmuestras(id);
        mbAgua.setCloroLibre(cloroLibre);
        mbAgua.setCloroTotal(cloroTotal);
        mbAgua.setPh(ph);
        return mbAguaRepository.save(mbAgua);
    }

    @Override
    public MbAgua guardarMbAgua(MbAgua mbAgua) {
        return mbAguaRepository.save(mbAgua);
    }

    @Override
    public MbAgua editarMbAgua(MbAgua mbAgua, Long id) {
        return mbAguaRepository.findById(id).map(mbaguaDb -> {
            mbAgua.setIdmuestras(id);
            return mbAguaRepository.save(mbAgua);
        }).orElseThrow(() -> new EntityNotFoundException("Analisis no encontrado."));
    }

    @Override
    public Optional<VistaMbAgua> recuperarMbAgua(Long id) {
        return vistaMbAguaRepository.findById(id);
    }

    @Override
    public List<VistaMbAgua> recuperarMbAguas() {
        return vistaMbAguaRepository.findAll();
    }

    @Override
    public MbAgua guardarFechaAnalisis(LocalDate fechaAnalisis, Long id) {
        return mbAguaRepository.findById(id).map(mbAgua -> {
            mbAgua.setFechaAnalisis(fechaAnalisis);
            return mbAguaRepository.save(mbAgua);
        }).orElseThrow(() -> new EntityNotFoundException("Analisis no encontrado."));
    }

    @Override
    public MbAgua guardarLimitesMohos(Boolean mohosLimite, Long id) {
        return mbAguaRepository.findById(id).map(mbAgua -> {
            mbAgua.setMohosLimite(mohosLimite);
            return mbAguaRepository.save(mbAgua);
        }).orElseThrow(() -> new EntityNotFoundException("Analisis no encontrado."));
    }

    @Override
    public boolean existeMbAgua(Long id) {
        return mbAguaRepository.existsById(id);
    }
}

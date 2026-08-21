package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.MbAgua;
import org.ignaciorodriguez.repository.MbAguaRepository;
import org.ignaciorodriguez.service.MbAguaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MbAguaServiceImpl implements MbAguaService {

    @Autowired
    MbAguaRepository mbAguaRepository;

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
    public Optional<MbAgua> recuperarMbAgua(Long id) {
        return mbAguaRepository.findById(id);
    }

    @Override
    public List<MbAgua> recuperarMbAguas() {
        return mbAguaRepository.findAll();
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

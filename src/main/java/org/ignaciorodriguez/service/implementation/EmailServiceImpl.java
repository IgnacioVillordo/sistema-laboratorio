package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.Email;
import org.ignaciorodriguez.modelo.VistaEmail;
import org.ignaciorodriguez.repository.EmailRepository;
import org.ignaciorodriguez.repository.VistaEmailRepository;
import org.ignaciorodriguez.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    EmailRepository emailRepository;
    @Autowired
    VistaEmailRepository vistaEmailRepository;


    @Override
    public Email guardarEmail(Email email) {
        return emailRepository.save(email);
    }

    @Override
    public Email editarEmail(Email email, Long id) {
        return emailRepository
                .findById(id)
                .map(emailDb -> {
                    email.setIdemails(id);
                    return emailRepository.save(email);
                })
                .orElseThrow(() -> new EntityNotFoundException("Email no encontrado"));
    }

    @Override
    public Optional<Email> recuperarEmail(Long id) {
        return emailRepository.findById(id);
    }

    @Override
    public List<Email> recuperarEmails() {
        return emailRepository.findAll();
    }

    @Override
    public Optional<VistaEmail> recuperarVistaEmail(Long id) {
        return vistaEmailRepository.findById(id);
    }
}

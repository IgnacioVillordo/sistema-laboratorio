package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.Email;
import org.ignaciorodriguez.modelo.VistaEmail;

import java.util.List;
import java.util.Optional;

public interface EmailService {

    Email guardarEmail(Email email);
    Email editarEmail(Email email, Long id);
    Optional<Email> recuperarEmail(Long id);
    List<Email> recuperarEmails();
    Optional<VistaEmail> recuperarVistaEmail(Long id);
}

package org.ignaciorodriguez.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue
    private Long idemails;
    private String destinatario;
    private String cuerpo;
    private String archivo;
    private LocalDateTime hora;
    private String remitente;
    private String procedencia;


}

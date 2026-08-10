package org.ignaciorodriguez.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vistaentregas")
@Immutable
public class VistaEntregas {

    @Id
    private Long idmuestras;
    private String procedencia;
    private String solicitante;
    private String tipo;
    private String persona;
    private LocalDateTime hora;
    private LocalDate fechaAnalisis;
    private String identificaciones;
}

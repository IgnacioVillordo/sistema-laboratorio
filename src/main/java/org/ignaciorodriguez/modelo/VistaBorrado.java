package org.ignaciorodriguez.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Immutable
public class VistaBorrado {
    @Id
    private long idmuestras;
    private String procedencia;
    private String solicitante;
    private LocalDate fechaMuestreo;
    private LocalDate fechaAnalisis;
    private String tipo;
}

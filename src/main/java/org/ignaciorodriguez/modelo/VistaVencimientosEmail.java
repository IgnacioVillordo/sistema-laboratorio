package org.ignaciorodriguez.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "vista_vencimientos_email")
@Immutable
public class VistaVencimientosEmail {

    @Id
    private Long idmuestras;
    private String email;
    private LocalDate fechaVencimiento;
    private String tipo;
    private String realizadoPor;
    private String procedencia;
}

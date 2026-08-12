package org.ignaciorodriguez.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "vistavencimientos")
@Immutable
public class VistaVencimientos {

    @Id
    private Long idmuestras;
    private Boolean aviso;
    private String procedencia;
    private String solicitante;
    private LocalDate fechaVencimiento;
    private String tipo;
    private Boolean seleccionadoVencimiento;
}

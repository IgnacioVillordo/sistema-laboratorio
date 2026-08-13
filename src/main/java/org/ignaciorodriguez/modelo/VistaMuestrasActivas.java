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
@Table(name = "vista_muestras_pendientes")
@Immutable
public class VistaMuestrasActivas {

    @Id
    private Long idmuestras;
    private String procedencia;
    private String solicitante;
    private String numeroEstablecimiento;
    private LocalDate fechaMuestreo;
    private LocalDate fechaAnalisis;
    private String realizadoPor;
    private LocalDateTime entrada;
    private Boolean pago;
    private Boolean factura;
    private String tipo;
    private String identificaciones;
}

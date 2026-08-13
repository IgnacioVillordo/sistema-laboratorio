package org.ignaciorodriguez.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.ignaciorodriguez.enums.Estado;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "vista_busqueda")
@Immutable
public class VistaBusqueda {

    @Id
    private Long idmuestras;
    private String procedencia;
    private String solicitante;
    private String numeroEstablecimiento;
    private LocalDate fechaMuestreo;
    private LocalDate fechaAnalisis;
    private String realizadoPor;
    private Double precioTotal;
    private Boolean pago;
    private Boolean factura;
    private String tipo;
    private String identificaciones;
    private Estado estado;
}

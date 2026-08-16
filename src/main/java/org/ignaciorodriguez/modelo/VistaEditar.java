package org.ignaciorodriguez.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "vistaeditar")
public class VistaEditar {
    @Id
    private Long idmuestras;
    private String solicitante;
    private String procedencia;
    private String numeroEstablecimiento;
    private LocalDate fechaMuestreo;
    private String realizadoPor;
    private Double precioTotal;
    private boolean pago;
    private boolean factura;
    private String tipo;
    private String lote;
    private String identificacion;
    private LocalDate fechaElaboracion;
    private String lugarMuestreo;
    private LocalDate fechaVencimiento;
    private String aguaTipo;
    private Long idcliente;
}

package org.ignaciorodriguez.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString
@Table(name = "muestras")
public class Muestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmuestras;
    private Long idcliente;
    private String solicitante;
    private String numeroEstablecimiento;
    private LocalDate fechaMuestreo;
    private LocalDate fechaAnalisis;
    private String realizadoPor;
    private String loteAlimento;
    private String identificacion;
    private String tipo;
    private Date fechaVencimiento;
    private Date fechaElaboracion;
    private String descripcionMuestra;
    private String observaciones;
    private String lugarMuestreo;
    private String conclusion;
    private String recomendacion;
    private String aguaTipo;
    private String notas;
    private Boolean ponerFechaVencimiento;
    private Boolean aHacer;
}

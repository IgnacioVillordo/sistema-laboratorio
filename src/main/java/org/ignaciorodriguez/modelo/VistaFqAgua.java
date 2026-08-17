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
@Table(name = "vistafqagua")
@Immutable
public class VistaFqAgua {
    @Id
    private Long vistatabla_idmuestras;
    private String procedencia;
    private String vistatabla_solicitante;
    private String vistatabla_numeroEstablecimiento;
    private Double porcentajeTotalCloro;
    private Double ph;
    private String olor;
    private String color;
    private String turbidez;
    private Double alcalinidad;
    private Double durezatotal;
    private Double conductividad;
    private Double solidosDisueltos;
    private String hierro;
    private String nitrato;
    private String nitritos;
    private String sulfatos;
    private LocalDate vistatabla_fechaMuestreo;
    private LocalDate vistatabla_fechaAnalisis;
    private String vistatabla_realizadoPor;
    private String vistatabla_tipo;
    private String observaciones;
    private LocalDate fechaVencimientos;
    private String conclusion;
    private Boolean ponerFechaVencimiento;
}

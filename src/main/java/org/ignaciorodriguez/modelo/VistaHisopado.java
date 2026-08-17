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
@Table(name = "vistahisopado")
@Immutable
public class VistaHisopado {

    @Id
    private Long vistatabla_idmuestras;
    private String procedencia;
    private String vistatabla_solicitante;
    private String vistatabla_numeroEstablecimiento;
    private LocalDate vistatabla_fechaMuestreo;
    private LocalDate fechaAnalisis;
    private String vistatabla_realizadoPor;
    private String vistatabla_tipo;
    private String observaciones;
    private String germenes;
    private String coliformesTotales;
    private String coliformesFecales;
    private String escherichia;
    private String staphilococos;
    private String enterobacterias;
    private String salmonella;
    private String listeria;
    private String mohos;
    private String conclusion;
    private String germenesPotencia;
    private String totalesPotencia;
    private String staphilococosPotencia;
    private String limiteGermenes;
    private String limiteTotales;
    private String vibrio;
}

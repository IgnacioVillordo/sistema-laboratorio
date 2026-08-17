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
@Table(name = "vistambagua")
@Immutable
public class VistaMbAgua {

    @Id
    private Long vistatabla_idmuestras;
    private String procedencia;
    private String vistatabla_solicitante;
    private String vistatabla_numeroEstablecimiento;
    private Double vistatabla_porcentajeTotalCloro;
    private Double vistatabla_ph;
    private LocalDate vistatabla_fechaMuestreo;
    private LocalDate vistatabla_fechaAnalisis;
    private String vistatabla_realizadoPor;
    private String vistatabla_tipo;
    private String observaciones;
    private LocalDate fechaVencimiento;
    private String germenes;
    private String coliformesTotales;
    private String coliformesFecales;
    private String escherichia;
    private String pseudomona;
    private String caracteresOrganolepticos;
    private Double cloroLibre;
    private String staphilococos;
    private String streptococos;
    private String conclusion;
    private Boolean vencimiento;
    private String mohos;
    private Boolean ponerFechaVencimiento;
    private String shegella;
}

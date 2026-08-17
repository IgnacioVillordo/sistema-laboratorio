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
@Table(name = "vistaefluentes")
@Immutable
public class VistaEfluentes {
    @Id
    private Long idmuestras;
    private String procedencia;
    private String solicitante;
    private LocalDate fechaMuestreo;
    private String tipo;;
    private String numeroEstablecimiento;
    private String realizadoPor;
    private Double ph;
    private String dqo;
    private String dbo;
    private String solidos10;
    private String solidos120;
    private String detergentes;
    private String grasas;
    private String fosforo;
    private String nitrogeno;
    private String sustancias;
    private String coliformesTotales;
    private String coliformesFecales;
    private String escherichia;
    private String conducitivdad;
    private String hidrocarburos;
    private String nitratos;
    private String cloro;
    private String lugarMuestreo;
    private String observaciones;
    private String conclusion;
    private String sulfuros;
}

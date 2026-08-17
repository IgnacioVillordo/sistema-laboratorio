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
@Table(name = "vista_muestras_pendientes")
@Immutable
public class VistaMbChocolates {
    @Id
    private Long idmuestras;
    private String tipo;
    private String numeroEstablecimiento;
    private String muestraAnalizada;
    private LocalDate fechaElaboracion;
    private LocalDate fechaVencimiento;
    private String solicitante;
    private LocalDate fechaMuestreo;
    private LocalDate fechaAnalisis;
    private String realizadoPor;
    private String germenes;
    private String coliformesTotales;
    private String coliformesFecales;
    private String escherichia;
    private String mohos;
    private String observaciones;
    private String staphilococos;
    private String salmonella;
    private String conclusion;
}

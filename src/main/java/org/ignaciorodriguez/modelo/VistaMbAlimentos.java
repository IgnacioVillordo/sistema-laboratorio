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
@Table(name = "vistambalimentos")
@Immutable
public class VistaMbAlimentos {
    @Id
    private Long idmuestras;
    private String muestraAnalizada;
    private LocalDate fechaElaboracion;
    private String tipo;
    private String numeroEstablecimiento;
    private LocalDate fechaVencimiento;
    private String solicitante;
    private LocalDate fechaMuestreo;
    private LocalDate fechaAnalisis;
    private String realizadoPor;
    private String descripcionMuestra;
    private String coliformesTotales;
    private String coliformesFecales;
    private String escherichia;
    private String staphilococos;
    private String salmonella;
    private String mohosLevaduras;
    private String lote;
    private String observaciones;
    private String conclusion;
    private String germenes;
    private String coliformesTotalesMetodo;
    private String coliformesFecalesMetodo;
    private String escherichiaMetodo;
    private String staphilococosMetodo;
    private String salmonellaMetodo;
    private String mohosLevadurasMetodo;
    private String germenesMetodo;
    private String escherichiah7;
    private String escherichia157;
    private String enterobacterias;
    private String listeria;
    private String perfringens;
    private String sulfito;
    private String campilobacter;
    private String escherichiah7Metodo;
    private String escherichia157Metodo;
    private String enterobacteriasMetodo;
    private String listeriaMetodo;
    private String perfringensMetodo;
    private String sulfitoMetodo;
    private String campilobacterMetodo;
    private String bacillus;
    private String bacillusMetodo;
    private String caracteristicas;
    private String caracteristicasMetodo;
    private String coliformesTotalesA30;
    private String coliformesTotalesa30Metodo;
    private String coliformesTotalesProbables;
    private String coliformesTotalesPRobablesMetodo;
    private String lactobacillus;
    private String lactobacillusMetodo;
    private String bacteriasLacticas;
    private String bacteriasLactivasMetodo;
    private String coliformesTotales45;
    private String coliformesTotales45Metodo;
    private String vibrio;
    private String vibrioMetodo;
    private String shigella;
    private String shigellaMetodo;
    private String vibrioCholerae;
    private String vibrioCholeraeMetodo;
}

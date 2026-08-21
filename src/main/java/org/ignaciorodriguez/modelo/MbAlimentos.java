package org.ignaciorodriguez.modelo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mb_alimentos")
public class MbAlimentos {

    @Id
    private Long idmuestras;
    private String coliformesTotales;
    private String staphilococos;
    private String salmonella;
    private String escherichia;
    private String mohosLevaduras;
    private Integer potencia;
    private String coliformesFecales;
    private String germenes;
    private String coliformesTotalesMetodo;
    private String coliformesFecalesMetodo;
    private String staphilococosMetodo;
    private String salmonellaMetodo;
    private String escherichiaMetodo;
    private String mohosLevadurasMetodo;
    private String germenesMetodo;
    private String escherichiah7;
    private String escherichia157;
    private String enterobacterias;
    private String listeria;
    private String bacillus;
    private String perfringens;
    private String sulfito;
    private String campilobacter;
    private String escherichiah7Metodo;
    private String escherichia157Metodo;
    private String enterobacteriasMetodo;
    private String listeriaMetodo;
    private String bacillusMetodo;
    private String perfringensMetodo;
    private String sulfitoMetodo;
    private String campilobacterMetodo;
    private String caracteristicas;
    private String caracteristicasMetodo;
    private String coliformesTotalesA30;
    private String coliformesTotalesa30Metodo;
    private String coliformesTotalesProbables;
    private String coliformesTotalesPRobablesMetodo;
    private String lactobacillus;
    private String bacteriasLacticas;
    private String coliformesTotales45;
    private String lactobacillusMetodo;
    private String bacteriasLactivasMetodo;
    private String coliformesTotales45Metodo;
    private String vibrio;
    private String vibrioMetodo;
    private String shigella;
    private String shigellaMetodo;
    private String vibrioCholerae;
    private String vibrioCholeraeMetodo;

}

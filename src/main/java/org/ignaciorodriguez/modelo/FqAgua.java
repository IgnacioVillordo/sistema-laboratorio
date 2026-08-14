package org.ignaciorodriguez.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fqagua")
public class FqAgua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idfqagua;
    private Long idmuestras;
    private Double ph;
    private Double cloroTotla;
    private String olor;
    private String color;
    private String turbidez;
    private Double alcalinidad;
    private Double durezatotal;
    private Double conductividad;
    private Double solidosDisueltos;
    private String hierro;
    private String nitratos;
    private String nitritos;
    private String sulfatos;
    private String observaciones;
    private String conclusion;
}

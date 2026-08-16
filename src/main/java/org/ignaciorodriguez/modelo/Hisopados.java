package org.ignaciorodriguez.modelo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.concurrent.Immutable;

@Getter
@Setter
@Entity
@Table(name = "hisopados")
public class Hisopados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idhisopados;
    private Long idmuestras;;
    private String coliformesTotales;
    private String coliformesFecales;
    private String escherichia;
    private String germenes;
    private String staphilococos;
    private String enterobacterias;
    private String germenesPotencia;
    private String totalesPotencia;
    private String staphilococosPotencia;
    private String limiteGermenes;
    private String limiteTotales;
    private String salmonella;
    private String mohos;
    private String listeria;
    private String vibrio;
}

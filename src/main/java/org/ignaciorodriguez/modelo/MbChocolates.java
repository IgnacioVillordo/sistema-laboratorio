package org.ignaciorodriguez.modelo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.concurrent.Immutable;

@Getter
@Setter
@Entity
@Table(name = "mbchocolates")
@Immutable
public class MbChocolates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmbChocolates;
    private Long idmuestras;
    private String germenes;
    private String coliformesTotales;
    private String coliformesFecales;
    private String escherichia;
    private String mohos;
    private String conclusion;
    private String salmonella;
    private String staphilococos;
}

package org.ignaciorodriguez.modelo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vista_muestras_pendientes")
public class TablaNutricional {

    @Id
    private Long idmuestras;
    private String marca;
    private Integer calorias;
    private Double kjul;
    private Double carbohidratos;
    private Double proteinas;
    private Double grasasTotales;
    private Double grasasSaturadas;
    private Double grasasTrans;
    private Double grasasMonoinsaturadas;
    private Double grasasPoliinsaturadas;
    private Double colesterol;
    private Double fibraAlimentaria;
    private Double sodio;
    private Integer VDCalorias;
    private Integer VDCarbohidratos;
    private Integer VDProteinas;
    private Integer VDGrasasTotales;
    private Integer VDGrasasSaturadas;
    private Integer VDGrasasMonoinsaturadas;
    private Integer VDGrasasPoliinsaturadas;
    private Integer VDColesterol;
    private Integer VDGrasasTrans;
    private Integer VDFibraAlimentaria;
    private Integer VDSodio;
    private String porcion;
    private String unidad;
    private Double azucares;
    private Double VDAzucares;
    private Double almidon;
    private Double VDAlmidon;
    private String porcionesPorEnvases;
    private Double azucaresAnadidos;
    private Double VDAzucaresAnadidos;
}

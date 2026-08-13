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
@Table(name = "mb_agua")
@Immutable
public class MBAgua {

    @Id
    private Long idmbagua;
    private Long idmuestras;
    private String germenes;
    private String escherichia;
    private String pseudomona;
    private Double ph;
    private Double cloroTotal;
    private String caracteresOrganolepticos;
    private LocalDate fechaAnalisis;
    private String coliformesFecales;
    private Double cloroLibre;
    private String staphilococos;
    private String streptococos;
    private boolean vencimiento;
    private String mohos;
    private String mohosLimite;
    private String shigella;
}
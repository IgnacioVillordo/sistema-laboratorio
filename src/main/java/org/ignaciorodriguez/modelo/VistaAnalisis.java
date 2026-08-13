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
@Table(name = "vista_analisis")
@Immutable
public class VistaAnalisis {
    @Id
    private Long idmuestras;
    private String procedencia;
    private String solicitante;
    private LocalDate fechaMuestreo;
    private String tipo;
    private Boolean analizado;
    private Boolean seleccionado;
    private String identificacion;
    private String notas;
}

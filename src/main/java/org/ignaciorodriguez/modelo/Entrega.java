package org.ignaciorodriguez.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identregas;
    private Long idmuestras;
    private Long idusuario;
    private String persona;
    private LocalDateTime hora;
    private Boolean activo;
}

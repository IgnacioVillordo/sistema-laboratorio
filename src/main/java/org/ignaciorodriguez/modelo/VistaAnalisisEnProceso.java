package org.ignaciorodriguez.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@Entity
@Table(name = "vistaanalisisenproceso")
@Immutable
public class VistaAnalisisEnProceso {

    @Column(name = "procedencia")
    private String procedencia;

    @Id
    @Column(name = "idcliente")
    private Long idcliente;
}

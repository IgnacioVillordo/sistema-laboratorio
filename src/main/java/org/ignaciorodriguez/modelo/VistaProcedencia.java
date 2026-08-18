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
@Table(name = "vistaprocedencia")
@Immutable // de Hibernate, indica que es de solo lectura
public class VistaProcedencia {
    @Id
    @Column(name = "idcliente")
    private Long idcliente;

    @Column(name = "procedencia")
    private String procedencia;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "activo")
    private Boolean activo;
}

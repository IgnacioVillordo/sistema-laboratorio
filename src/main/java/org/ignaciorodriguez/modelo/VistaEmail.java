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
@Table(name = "vistatabla")
@Entity
@Immutable@Entity
public class VistaEmail {

    @Id
    @Column(name = "idmuestras")
    Long idmuestras;

    @Column(name = "email")
    String email;
}

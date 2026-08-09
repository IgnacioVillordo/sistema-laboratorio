package org.ignaciorodriguez.modelo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "datos_cliente")
@Entity
public class Cliente {

    @Column(name = "idcliente")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "empresa")
    String empresa;
    @Column(name = "nombre")
    String nombre;
    @Column(name = "direccion")
    String direccion;
    @Column(name = "ciudad")
    String ciudad;
    @Column(name = "telefono")
    String telefono;
    @Column(name = "email")
    String email;
    @Column(name = "cuit")
    String cuit;
    @Column(name = "anulado")
    Boolean anulado;
    @Column(name = "guardar")
    Boolean guardar;
}

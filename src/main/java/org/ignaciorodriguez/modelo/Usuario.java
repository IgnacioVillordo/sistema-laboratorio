package org.ignaciorodriguez.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idusuarios;
    private String nombre;
    private String contrasena;
    private int tipoUsuario;
}

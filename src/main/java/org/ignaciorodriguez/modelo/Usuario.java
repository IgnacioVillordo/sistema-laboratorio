package org.ignaciorodriguez.modelo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usuario {
    private String usuario;
    private String contrasena;
    private int tipoUsuario;
    private int id;
}

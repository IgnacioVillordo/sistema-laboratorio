package org.ignaciorodriguez.modelo;

import javax.swing.JPasswordField;

public class VerContrasena {
    public boolean ocultarContrasena(JPasswordField contrasena, boolean ver){
        if(ver == false){
            contrasena.setEchoChar('\u0000');
            return true;
        }else{
            contrasena.setEchoChar('\u25cf');
            return false;
        }
    }
}

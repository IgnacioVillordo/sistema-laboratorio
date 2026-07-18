package org.ignaciorodriguez.modelo;

import javax.swing.JPasswordField;

public class VerContraseña {
    public boolean ocultarContraseña(JPasswordField contraseña, boolean ver){
        if(ver == false){
            contraseña.setEchoChar('\u0000');
            return true;
        }else{
            contraseña.setEchoChar('\u25cf');
            return false;
        }
    }
}

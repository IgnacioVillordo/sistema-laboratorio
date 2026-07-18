package org.ignaciorodriguez.modelo;

import java.sql.Connection;
import org.ignaciorodriguez.utils.IpUtils;

import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Conexion {

    public static final String usuario = "root";
    public static final String contraseña = "1234";
    public static final String DATABASE_URL = IpUtils.obtenerIp();
    
    public Connection getConnection() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection(DATABASE_URL, usuario, contraseña);
        } catch (Exception ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    public boolean getConnection(String ip) {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.setLoginTimeout(3);
            conexion = (Connection) DriverManager.getConnection(DATABASE_URL, usuario, contraseña);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

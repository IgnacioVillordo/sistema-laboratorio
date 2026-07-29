package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.service.ArchivoService;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

public class ArchivoRepository {

    private final Conexion con;
    private final Logger logger = Logger.getLogger(ArchivoRepository.class.getName());

    public ArchivoRepository(Conexion con) {
        this.con = con;
    }

    public String recuperarMySQL() {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select ruta from rutas where nombre = 'MySQL'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return (String) rs.getObject(1);
            }
        } catch (Exception e) {
            logger.severe( "Error al recuperar ruta, " + e);
        }
        return null;
    }
}

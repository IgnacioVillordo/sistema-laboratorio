package org.ignaciorodriguez.repository;

import lombok.extern.slf4j.Slf4j;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

@Slf4j
public class UsuarioRepository {
    private static final Logger logger = Logger.getLogger(UsuarioRepository.class.getName());
    private final Conexion con = new Conexion();

    public boolean insertarUsuario(Usuario usuario) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO usuarios (nombre,contrasena,tipoUsuario) VALUES (?,?,2)");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContrasena());
            int resultado = ps.executeUpdate();

            return resultado > 0;
        } catch (SQLException e) {
            logger.severe("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    public DefaultComboBoxModel<String> recuperarUsuarios() {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel();
        modelo.removeAllElements();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("SELECT nombre FROM usuarios");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addElement(rs.getString("nombre"));
            }
            return modelo;
        } catch (Exception e) {
            logger.severe("Error al recuperar usuarios: " + e.getMessage());
            return null;
        }
    }

    public boolean eliminarUsuario(Usuario usuario) {

        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM usuarios WHERE nombre = ?");
            ps.setString(1, usuario.getUsuario());
            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (Exception e) {
            logger.severe("Error al insertar usuario, " + e);
            return false;
        }
    }

    public boolean modificarUsuario(org.ignaciorodriguez.modelo.Usuario usuario) {//se modifica la contraseña del usuario seleccionado
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("UPDATE `laboratorio`.`usuarios` SET `contrasena` = ? WHERE nombre = ?");
            ps.setString(1, usuario.getContrasena());
            ps.setString(2, usuario.getUsuario());
            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (Exception e) {
            logger.severe("Error al modificar usuario, " + e);
            return false;
        }
    }

    public String consultaUsuarioNombre(String s) { // se obtiene el
        String nombre = "";
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select nombre from usuarios where contrasena = ?");
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre = String.valueOf(rs.getObject("nombre"));
                return nombre;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener nombre de usuario, " + e);
            return null;
        }
        return null;
    }

    public boolean consultaExisteUsuario(String pass) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("SELECT nombre, tipousuario FROM usuarios WHERE contrasena = ?");
            ps.setString(1, pass);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            logger.severe("Error al consultar usuario, " + e);
            return false;
        }
    }

    public int consultaUsuarioTipo(String s) {
        int tipo = -1;
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("SELECT tipousuario FROM usuarios WHERE contrasena = ?");
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tipo = rs.getInt("tipousuario");
            }
        } catch (Exception e) {
            logger.severe("Error al consultar tipo de usuario, " + e);
        }
        return tipo;
    }

    public Usuario consultaUsuario(String nombre) {
        Usuario u = new Usuario();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("SELECT idusuarios, nombre, contrasena FROM usuarios WHERE nombre = ?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    switch (i) {
                        case 1: {
                            u.setId(rs.getInt("idusuarios"));
                        }
                        case 2: {
                            u.setUsuario(rs.getString("nombre"));
                        }
                        case 3: {
                            u.setContrasena(rs.getString("contrasena"));
                        }
                    }
                }
            }
            return u;
        } catch (Exception e) {
            logger.severe("Error al consultar usuario: " + e.getMessage());
            return null;
        }
    }

    public int obtenerIdUsuario(String nombre) {
        int aux = -1;
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("SELECT idusuarios FROM usuarios WHERE nombre = ?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aux = rs.getInt(1);
            }
            return aux;
        } catch (Exception e) {
            logger.severe("Error al obtener usuario. " + e);
            return aux;
        }
    }

    public DefaultTableModel recuperarUsuariosTabla() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        String[] aux = new String[2];
        modeloTabla.addColumn("Nombre de usuario");
        modeloTabla.addColumn("Tipo de usuario");
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("SELECT nombre, tipoUsuario FROM usuarios");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                aux[0] = rs.getString("nombre");
                aux[1] = rs.getInt("tipoUsuario") == 1 ? "Administrador" : "Normal";
                modeloTabla.addRow(aux);
            }
            return modeloTabla;
        } catch (Exception e) {
            logger.severe("Error al recuperar usuarios, " + e);
            return null;
        }
    }
}

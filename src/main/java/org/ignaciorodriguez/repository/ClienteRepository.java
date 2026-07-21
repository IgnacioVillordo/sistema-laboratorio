package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Cliente;
import org.ignaciorodriguez.modelo.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClienteRepository {
    private static final Logger logger = Logger.getLogger(ClienteRepository.class.getName());
    Conexion con = new Conexion();

    public boolean agregarCliente(Cliente c) {
        List<String> columnas = new ArrayList<>();
        List<String> valores = new ArrayList<>();

        if (c.getEmpresa() != null && !c.getEmpresa().isEmpty()) {
            columnas.add("empresa");
            valores.add(c.getEmpresa());
        }
        if (c.getNombre() != null && !c.getNombre().isEmpty()) {
            columnas.add("nombre");
            valores.add(c.getNombre());
        }
        columnas.add("direccion");
        valores.add(valorOrDefault(c.getDireccion()));
        columnas.add("ciudad");
        valores.add(valorOrDefault(c.getCiudad()));
        columnas.add("telefono");
        valores.add(valorOrDefault(c.getTelefono()));
        columnas.add("email");
        valores.add(valorOrDefault(c.getEmail()));
        columnas.add("cuit");
        valores.add(valorOrDefault(c.getCuit()));

        String sql = "insert into datos_cliente (" + String.join(",", columnas) + ") values ("
                + columnas.stream().map(x -> "?").collect(Collectors.joining(",")) + ")";

        try (Connection conexion = con.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            for (int i = 0; i < valores.size(); i++) {
                ps.setString(i + 1, valores.get(i));
            }
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    private String valorOrDefault(String valor) {
        return (valor == null || valor.isEmpty()) ? " " : valor;
    }

    public boolean editarCliente(Cliente c, int idmuestras) {
        List<String> columnas = new ArrayList<>();
        List<String> valores = new ArrayList<>();

        if (c.getEmpresa() != null && !c.getEmpresa().isEmpty()) {
            columnas.add("empresa");
            valores.add(c.getEmpresa());
        }
        if (c.getNombre() != null && !c.getNombre().isEmpty()) {
            columnas.add("nombre");
            valores.add(c.getNombre());
        }
        columnas.add("direccion");
        valores.add(valorOrDefault(c.getDireccion()));
        columnas.add("ciudad");
        valores.add(valorOrDefault(c.getCiudad()));
        columnas.add("telefono");
        valores.add(valorOrDefault(c.getTelefono()));
        columnas.add("email");
        valores.add(valorOrDefault(c.getEmail()));
        columnas.add("cuit");
        valores.add(valorOrDefault(c.getCuit()));

        String sql = "update datos_cliente set" + columnas.stream().map(x -> x + " = ?").collect(Collectors.joining(",")) + " where idmuestras = ?";

        try (Connection conexion = con.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            int i = 1;
            for (String valor : valores) {
                ps.setString(i + 1, valor);
            }
            ps.setInt(i, idmuestras);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean borrarCliente(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update datos_cliente set anulado = 1 where idcliente = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al borrar cliente, " + e);
            return false;
        }
    }

    public boolean recuperarClienteBorrado(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update datos_cliente set anulado = 0 where idcliente = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            logger.severe("Error al recuperar cliente borrado, " + e);
            return false;
        }
    }

    public int recuperarIdCliente(String procedencia) {
        int aux = -1;
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select idcliente from vistaprocedencia where procedencia = ?");
            ps.setString(1, procedencia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                aux = rs.getInt("idcliente");
            }
            return aux;
        } catch (Exception e) {
            logger.severe("Error al recuperar id del cliente, " + e);
            return -1;
        }
    }

    public String[] recuperarDatosCliente(int id) {
        String[] aux = new String[8];
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select * from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < aux.length; i++) {
                    if (i == 0) {
                        aux[0] = String.valueOf(rs.getInt("idcliente"));
                    } else {
                        aux[i] = rs.getString(i + 1);
                    }
                }
            }
            return aux;
        } catch (Exception e) {
            logger.severe("Error al recuperar datos, " + e);
            return null;
        }
    }

    public int recuperarIdCliente(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select idcliente from muestras where idmuestras = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            logger.severe("Error al recuperar id del cliente, " + e);
        }
        return -1;
    }

    public String obtenerProcedenciayNombre(int id) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select empresa, nombre from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getObject("empresa") + " " + rs.getObject("nombre");
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error, " + e);
        }
        return null;
    }

    public String[] obtenerProcedenciayNombreEmail(int id) {
        String[] aux = new String[2];
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select empresa, nombre from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aux[0] = rs.getObject("empresa").toString();
                aux[1] = rs.getObject("nombre").toString();
                return aux;
            }
            return null;
        } catch (Exception e) {
            logger.severe("Error al obtener procedencia y nombre, " + e);
        }
        return null;
    }

    public ArrayList<Object> autocompletarProcedencia() {
        ArrayList<Object> arreglo = new ArrayList<>();
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select procedencia from vistaProcedencia");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arreglo.add(rs.getString("procedencia"));
            }
            return arreglo;
        } catch (Exception e) {
            logger.severe("Error, " + e);
            return null;
        }
    }

    public DefaultTableModel tablaProcedencia() {
        DefaultTableModel modeloTablaProcedencia = new DefaultTableModel();
        modeloTablaProcedencia.addColumn("Cliente");
        modeloTablaProcedencia.addColumn("Teléfono");
        modeloTablaProcedencia.addColumn("Email");
        String[] fila = new String[3];
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select procedencia, telefono, email from vistaprocedencia where anulado = 0");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                fila[0] = rs.getString("procedencia");
                fila[1] = rs.getString("telefono");
                fila[2] = rs.getString("email");
                modeloTablaProcedencia.addRow(fila);
            }
            return modeloTablaProcedencia;

        } catch (Exception e) {
            logger.severe("Error al procedencia, " + e);
            return null;
        }
    }

    public DefaultTableModel tablaProcedenciaBorrados() {
        DefaultTableModel modeloTablaProcedencia = new DefaultTableModel();
        modeloTablaProcedencia.addColumn("Cliente");
        modeloTablaProcedencia.addColumn("Teléfono");
        modeloTablaProcedencia.addColumn("Email");
        String[] fila = new String[3];
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select procedencia, telefono, email from vistaprocedencia where anulado = 1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                fila[0] = rs.getString("procedencia");
                fila[1] = rs.getString("telefono");
                fila[2] = rs.getString("email");
                modeloTablaProcedencia.addRow(fila);
            }
            return modeloTablaProcedencia;

        } catch (Exception e) {
            logger.severe("Error al procedencia, " + e);
            return null;
        }
    }

    public int obtenerIdCliente(String procedencia) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select idcliente from vistaProcedencia where "
                    + "procedencia = ?");
            ps.setString(1, procedencia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idcliente");
            }
        } catch (Exception e) {
            logger.severe("Error al obtener id de cliente, " + e);
            return -1;
        }
        return -1;
    }

    public String recuperarDireccion(int id) {
        String direccion = "";
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select direccion from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                direccion = rs.getString("direccion");
            }
            return direccion;
        } catch (Exception e) {
            JOptionPane.showInputDialog("Error al recuperar direccion del cliente, " + e);
            return null;
        }
    }

    public String recuperarCiudad(int id) {
        String ciudad = "";
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select ciudad from datos_cliente where idcliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ciudad = rs.getString("ciudad");
            }
            return ciudad;
        } catch (Exception e) {
            logger.severe("Error al recuperar ciudad " + e);
            return null;
        }
    }

    public String recuperarProcedencia(int id) {
        String procedencia = "";
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select procedencia from vistaanalisisenproceso where idcliente = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                procedencia = rs.getString("procedencia");
            }
            return procedencia;
        } catch (Exception e) {
            logger.severe("Error al recuperar procedencia " + e);
            return null;
        }
    }

    public boolean consultarGuardar(int idCliente) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select guardar from datos_cliente where idcliente = ?");
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("guardar") == 1;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos, " + e);
        }
        return false;
    }

    public boolean guardarSolicitanteGuardar(int idcliente, boolean guardar) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update datos_cliente set guardar = ? where idcliente= ?");
            ps.setInt(1, guardar ? 1 : 0);
            ps.setInt(2, idcliente);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.severe("Error al guardar datos, " + e);
        }
        return false;
    }

    public void guardarEmail(String email, int idcliente) {
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("update datos_cliente set email = ? where idcliente = ?");
            ps.setString(1, email.replace(" ", ""));
            ps.setInt(2, idcliente);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.severe("Error al guardar email, " + e);
        }
    }
}

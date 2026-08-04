package main.java.org.ignaciorodriguez.repository;

import main.java.org.ignaciorodriguez.modelo.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class EmailRepository {

    private final Conexion con;

    public EmailRepository(Conexion con) {
        this.con = con;
    }

    public DefaultTableModel recuperarEmailsEnviados() {
        DefaultTableModel emails = new DefaultTableModel();
        emails.addColumn("Hora");
        emails.addColumn("Procedencia");
        emails.addColumn("Destinatario");
        emails.addColumn("cuerpo");
        emails.addColumn("archivo");
        emails.addColumn("remitente");
        String[] aux = new String[6];
        try (Connection conexion = con.getConnection()) {
            PreparedStatement ps = conexion.prepareStatement("select destinatario, hora, cuerpo, archivo, remitente, procedencia from emails order by idemails desc");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                aux[0] = new SimpleDateFormat("dd/MM/yyyy   HH:mm").format(rs.getTimestamp("hora"));
                aux[1] = rs.getString("procedencia");
                aux[2] = rs.getString("destinatario").replace(" ", "");
                aux[3] = rs.getString("cuerpo");
                aux[4] = rs.getString("archivo");
                aux[5] = rs.getString("remitente");
                emails.addRow(aux);
            }
            return emails;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar emails enviados, " + e);
            return null;
        }
    }

}

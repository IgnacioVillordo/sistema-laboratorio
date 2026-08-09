package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.repository.ArchivoRepository;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchivoService {

    Conexion con = new Conexion();
    ArchivoRepository archivoRepository = new ArchivoRepository(con);

    public String devolverCopiaSeguridad(String ruta) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime ahora = LocalDateTime.now();
        String fecha = dtf.format(ahora);
        return ruta + "" + org.ignaciorodriguez.utils.SeparatorUtils.s + "respaldo_" + fecha + ".sql";

    }

    public boolean guardarRutas(String nombre, String ruta) {
        File archivo = null;
        if (nombre.equals("Reportes")) {
            archivo = new File("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "rutaDocumentos.txt");
        } else if (nombre.equals("Respaldo")) {
            archivo = new File("src" + org.ignaciorodriguez.utils.SeparatorUtils.s + "vista" + org.ignaciorodriguez.utils.SeparatorUtils.s + "rutaRespaldo.txt");
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(ruta);
            bw.flush();
            return true;
        } catch (IOException ex1) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex1);
            return false;
        }
    }

    public String recuperarRutas(String nombre) {
        String ruta = null;
        File archivo = null;
        try {
            switch (nombre) {
                case "Reportes":
                    archivo = new File(Objects.requireNonNull(getClass().getResource("/rutaDocumentos.txt")).toURI());
                    break;
                case "Respaldo":
                    archivo = new File(Objects.requireNonNull(getClass().getResource("/rutaRespaldo.txt")).toURI());
                    break;
                case "MySQL":
                    return archivoRepository.recuperarMySQL();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(Objects.requireNonNull(archivo)));
            ruta = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ruta;
    }

}

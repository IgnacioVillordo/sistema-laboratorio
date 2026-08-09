package org.ignaciorodriguez.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.ignaciorodriguez.modelo.Conexion;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IpUtils {
    private static final Dotenv dotenv = Dotenv.load();
    public static String obtenerIp() {
        File archivo = null;
        String ip = "";
        try {
            System.out.println(IpUtils.class.getResource("/ip.txt"));
            archivo = new File(IpUtils.class.getResource("/ip.txt").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(archivo));
            ip = br.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(Objects.equals(ip, "localhost")){
            return dotenv.get("DB_URL_ADMIN");
        } else {
            return dotenv.get("DB_URL_USER");
        }
    }
}

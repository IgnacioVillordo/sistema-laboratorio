package org.ignaciorodriguez.service;

import org.ignaciorodriguez.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActualizacionService {

    public final static int NO_ACTUALIZAR = 0;
    public final static int ACTUALIZAR = 1;
    public final static int ERROR_ACTUALIZAR = -1;

    public int consultarActualizacion() {
        String aux = "";
        try {
            URL url = new URL("http://138.36.236.245/actualizar.php");
            String agent = "Applet";
            String query = "query=";
            String type = "application/x-www-form-urlencoded";
            HttpURLConnection conexion = null;
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setConnectTimeout(3000);
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("User-Agent", agent);
            conexion.setRequestProperty("Content-Type", type);
            conexion.setRequestProperty("Content-Length", "" + query.length());

            OutputStream out;
            try {
                out = conexion.getOutputStream();
            } catch (SocketTimeoutException | ConnectException ex) {
                return ERROR_ACTUALIZAR;
            }
            out.write(query.getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            aux = in.readLine();
            in.close();
            return aux.equals(Main.VERSION) ? NO_ACTUALIZAR : ACTUALIZAR;
        } catch (IOException ex) {
            Logger.getLogger(ActualizacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -25962;
    }
}

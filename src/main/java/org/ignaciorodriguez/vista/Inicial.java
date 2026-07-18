package org.ignaciorodriguez.vista;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.modelo.Consultas;

public class Inicial {

    public final static String VERSION = "1.154";

    public static void main(String[] args) {
        System.out.println("VERSION = " + VERSION);
        System.setProperty("awt.useSystemAAFontSettings","on");
        String ip = null;
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        NewJFrame n = new NewJFrame();
        n.setVisible(true);
        Conexion con = new Conexion();
        int cont = 0;
        boolean conexion = false;
        for (int j = 0; j < 2; j++) {
            File archivo = null;
            try {
                archivo = new File(Inicial.class.getResource("/ip.txt").toURI());
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
            if (con.getConnection(ip)) {
                try {
                    File bd = new File(Inicial.class.getResource("/bd.txt").toURI());
                    List<String> lines = Files.readAllLines(bd.toPath());
                    if (!lines.isEmpty()) {
                        List<String> statements = new ArrayList<>();
                        String aux = "";
                        for (int i = 0; i < lines.size(); i++) {
                            aux += lines.get(i).trim() + " ";
                            if (lines.get(i).contains(";")) {
                                statements.add(aux);
                                aux = "";
                            }
                        }
                        statements.forEach(st -> {
                            Connection conn = con.getConnection();
                            try {
                                java.sql.PreparedStatement ps = conn.prepareStatement(st);
                                ps.executeUpdate();
                            } catch (Exception e) {
                                System.err.println(e);
                            } finally {
                                try {
                                    conn.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        BufferedWriter bw = null;
                        try {
                            bw = new BufferedWriter(new FileWriter(bd, false));
                            bw.write("");
                            bw.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                bw.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                Consultas c = Consultas.getInstancia();
                int actualizacion = -1;
                actualizacion = c.consultarActualizacion();
                if (actualizacion == Consultas.ACTUALIZAR) {
                    Descargando d = new Descargando(n, true);
                    d.setVisible(true);
                }
                Principal p = new Principal();
                p.setVisible(true);
                n.dispose();
                conexion = true;
                break;

            } else {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(archivo, false));
                    if (ip.equals("localhost")) {
                        bw.write("192.168.1.50");
                    } else {
                        bw.write("localhost");
                    }
                    bw.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        bw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            cont++;
        }
        if (!conexion) {
            JOptionPane.showMessageDialog(null, "Error al ingresar al sistema, compruebe que la base de datos este abierta.");
            System.exit(0);
        }
        n.dispose();

    }
}

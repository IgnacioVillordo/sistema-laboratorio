package org.ignaciorodriguez.modelo;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Determinacion {

    private String nombre;
    private String unidadDefault;
    private JPanel panel;
    private String resultado;
    private String metodo;
    private String nombreDB;
    private boolean activado;
    private List<String> unidades = new ArrayList<>(List.of("mg/g", "mg/Kg", "mg/l", "%", "", "\u00B0C"));
    private HashMap<String, Component> componentes;
    String unidadResultado;

    public Determinacion(String nombre, String unidadDefault, JPanel panel,
            String resultado, String metodo, String nombreDB, boolean activado,
            String[] unidades, HashMap<String, Component> componentnes) {
        this.nombre = nombre;
        this.unidadDefault = unidadDefault;
        this.panel = panel;
        this.resultado = resultado;
        this.metodo = metodo;
        this.nombreDB = nombreDB;
        this.activado = activado;
        try {
            this.unidades.addAll(Arrays.asList(unidades));
        } catch (java.lang.NullPointerException e) {
        }
        this.componentes = componentes;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        if (metodo != this.metodo && metodo != null && !metodo.isBlank()) {
            this.metodo = metodo;
        }
    }

    public HashMap<String, Component> getComponentes() {
        return componentes;
    }

    public void setComponentes(HashMap<String, Component> componentes) {
        this.componentes = componentes;
    }

    public String getUnidadDefault() {
        return unidadDefault;
    }

    public void setUnidadDefault(String unidadDefault) {
        this.unidadDefault = unidadDefault;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<String> unidades) {
        this.unidades = unidades;
    }

    public Determinacion(String nombre, String nombreDB) {
        this.nombreDB = nombreDB;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void guardarResultado() {
        String campo = ((JTextField) componentes.get("campo")).getText();
        String unidad = Objects.toString(((JLabel) componentes.get("etiquetaUnidad")).getText(), "");
        setResultado(campo + " " + unidad);
    }

    public String getNombreDB() {
        return nombreDB;
    }

    public void setNombreDB(String nombreDB) {
        this.nombreDB = nombreDB;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public void formatearResultado(String dbResult) {
        unidadResultado = unidades.stream()
                .filter(u -> !u.isEmpty() && dbResult.contains(u))
                .findFirst()
                .orElse("");

        this.setResultado(unidadResultado.isEmpty()
                ? dbResult.substring(0, dbResult.length() - 1)
                : dbResult.substring(0, dbResult.length() - 1 - unidadResultado.length()));
    }

    public void formatearResultadoGenerar(String dbResult) {
        this.resultado = dbResult;
    }

    public void guardarMetodo() {
        String metodoUser = JOptionPane.showInputDialog("Ingrese el método para  " + this.nombre, metodo);
        this.setMetodo(metodoUser);
    }

    @Override
    public String toString() {
        return this.getNombre() + ": " + this.getResultado();
    }

    public void llenarCampos() {
        ((JTextField) componentes.get("campo")).setText(getResultado() == "-1" ? "" : getResultado());
        ((JLabel) componentes.get("etiquetaUnidad")).setText(unidadResultado);
    }
}

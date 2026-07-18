package org.ignaciorodriguez.modelo;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeterminacionHumedad extends Determinacion {

    private boolean activarPieza = true;

    public DeterminacionHumedad(String nombre,
            String unidadDefault,
            JPanel panel,
            String resultado,
            String metodo,
            String nombreDB,
            boolean activado,
            String[] unidades,
            HashMap<String, Component> componentnes) {
        super(nombre, unidadDefault, panel, resultado, metodo, nombreDB, activado, unidades, componentnes);
    }

    public boolean isActivarPieza() {
        return activarPieza;
    }

    public void setActivarPieza(boolean activarPieza) {
        this.activarPieza = activarPieza;
    }

    @Override
    public void guardarResultado() {
        String res = "";
        String unidadMedio = "";
        if (!activarPieza) {
            res = ((JTextField) super.getComponentes().get("campo")).getText().trim() + " ";
            unidadMedio = ((JLabel) super.getComponentes().get("etiquetaPieza")).getText().trim() + " ";
            unidadMedio = unidadMedio.substring(0, unidadMedio.indexOf(" "));
            super.setResultado(res + unidadMedio);
            return;
        }
        res = ((JTextField) super.getComponentes().get("campo")).getText().trim() + " ";
        unidadMedio = ((JLabel) super.getComponentes().get("etiquetaPieza")).getText().trim() + " ";
        String valorUnidad = ((JTextField) super.getComponentes().get("campoPieza")).getText().trim() + " ";
        String unidadPieza = ((JLabel) super.getComponentes().get("etiquetaUnidadPieza")).getText();
        super.setResultado(res + unidadMedio + valorUnidad + unidadPieza);
    }

    @Override
    public void formatearResultado(String dbResult) {
        if (dbResult == null || dbResult.isEmpty()) {
            return;
        }

        String separador = " para una pieza de ";
        if (dbResult.contains(separador)) {
            String[] partes = dbResult.split(separador);

            int primerEspacio = partes[0].indexOf(" ");
            if (primerEspacio != -1) {
                this.setResultado(partes[0].substring(0, primerEspacio));
                this.unidadResultado = partes[0].substring(primerEspacio + 1);
            }

            String partePieza = partes[1].trim();
            int espacioPieza = partePieza.indexOf(" ");
            if (espacioPieza != -1) {
                String valorNum = partePieza.substring(0, espacioPieza);
                String unidadP = partePieza.substring(espacioPieza + 1);
                this.getComponentes().get("campoPieza").setName(valorNum);
                this.getComponentes().get("etiquetaUnidadPieza").setName(unidadP);
            }
        } else {
            ((JTextField) super.getComponentes().get("campoPieza")).setEnabled(false);
            ((JTextField) super.getComponentes().get("campoPieza")).setBackground(new Color(255, 210, 210));
            ((JLabel) super.getComponentes().get("etiquetaPieza")).setBackground(new Color(255, 210, 210));
            ((JLabel) super.getComponentes().get("etiquetaUnidadPieza")).setBackground(new Color(255, 210, 210));
            setActivarPieza(false);
            super.formatearResultado(dbResult);
        }
    }

    @Override
    public void llenarCampos() {
        JTextField campo = (JTextField) getComponentes().get("campo");
        JLabel etiquetaPieza = (JLabel) getComponentes().get("etiquetaPieza");
        JTextField campoPieza = (JTextField) getComponentes().get("campoPieza");
        JLabel etiquetaUnidadPieza = (JLabel) getComponentes().get("etiquetaUnidadPieza");

        // Llenar campos principales
        campo.setText(getResultado().equals("-1") ? "" : getResultado());
        if (unidadResultado != null) {
            etiquetaPieza.setText(unidadResultado + " para una pieza de ");
        }

        // Llenar campos de la pieza (Ahora correctamente mapeados)
        if (campoPieza.getName() != null) {
            campoPieza.setText(campoPieza.getName()); // Setea el "500"
        }
        if (etiquetaUnidadPieza.getName() != null) {
            etiquetaUnidadPieza.setText(etiquetaUnidadPieza.getName()); // Setea el "g"
        }
    }

}

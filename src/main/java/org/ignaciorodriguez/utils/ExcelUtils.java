package org.ignaciorodriguez.utils;

import com.mysql.cj.util.StringUtils;

public class ExcelUtils {

    public String formatearEntradaExcel(String aux) {
        if (aux == null || aux.contains("-2") || aux.isBlank() || aux.contains("-1")) {
            return "N/A";
        }
        return aux;
    }

    public String extraerAusenciaPresencia(String aux) {
        if (aux.toLowerCase().contains("presencia")) {
            return "1";
        }
        return "0";
    }

    public double extraerNumero(String texto) {
        if (texto != null) {
            texto = texto.trim();
        }
        if (texto == null || texto.isEmpty() || texto.contains("-2") || texto.contains("-1")) {
            return Double.NaN;
        }

        if (StringUtils.startsWithIgnoreCase(texto, "menor")) {
            return 0;
        }
        if (texto.toLowerCase().contains("mayor a")) {
            return Double.parseDouble(texto.toLowerCase().replace("Mayor a ", "").trim().replaceAll("[^0-9.]", ""));
        }
        String soloNumeros = "";
        if (texto.contains(" ")) {
            Double ret = Double.parseDouble(texto.substring(0, texto.trim().indexOf(" ")).replaceAll("[^0-9.]", ""));
            return ret;
        }
        soloNumeros = texto.replaceAll("[^0-9.]", "");
        try {
            return Double.parseDouble(soloNumeros);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public String procesarCeldaGrafico(String valor) {
        return valor.replaceAll("[^0-9.]", "");

    }

}

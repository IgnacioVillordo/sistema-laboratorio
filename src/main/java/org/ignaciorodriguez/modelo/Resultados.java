package org.ignaciorodriguez.modelo;

import java.sql.Date;


public class Resultados {
    int idmuestras;
    double ph,conductividad, clorototal, cloroLibre, solidosdisueltos, alcalinidad, durezatotal;
    String escherichia, pseudomona, observaciones, caracteresOrgasnolepticos, olor, 
            color, hierro, nitratos, nitritos, sulfatos, turbidez, conclusion, tipo,
            streptococos, staphilococos, coliformesTotales, coliformesFecales, germenes, mohos, shigella;

    public String getShigella() {
        return shigella;
    }

    public void setShigella(String shigella) {
        this.shigella = shigella;
    }
    java.sql.Date fechaAnalisis;

    public String getMohos() {
        return mohos;
    }

    public void setMohos(String mohos) {
        this.mohos = mohos;
    }
    
    public double getCloroLibre() {
        return cloroLibre;
    }

    public void setCloroLibre(double cloroLibre) {
        this.cloroLibre = cloroLibre;
    }

    public String getStreptococos() {
        return streptococos;
    }

    public void setStreptococos(String streptococos) {
        this.streptococos = streptococos;
    }

    public String getStaphilococos() {
        return staphilococos;
    }

    public void setStaphilococos(String staphilococos) {
        this.staphilococos = staphilococos;
    }

    
    
    public String getTipo() {
        return tipo;
    }

    public String getColiformesFecales() {
        return coliformesFecales;
    }

    public void setColiformesFecales(String coliformesFecales) {
        this.coliformesFecales = coliformesFecales;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaAnalisis() {
        return fechaAnalisis;
    }

    public void setFechaAnalisis(Date fechaAnalisis) {
        this.fechaAnalisis = fechaAnalisis;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getCaracteresOrgasnolepticos() {
        return caracteresOrgasnolepticos;
    }

    public void setCaracteresOrgasnolepticos(String caracteresOrgasnolepticos) {
        this.caracteresOrgasnolepticos = caracteresOrgasnolepticos;
    }

    public String getTurbidez() {
        return turbidez;
    }

    public void setTurbidez(String turbidez) {
        this.turbidez = turbidez;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public double getClorototal() {
        return clorototal;
    }

    public void setClorototal(double clorototal) {
        this.clorototal = clorototal;
    }

    public double getAlcalinidad() {
        return alcalinidad;
    }

    public void setAlcalinidad(double alcalinidad) {
        this.alcalinidad = alcalinidad;
    }

    public double getDurezatotal() {
        return durezatotal;
    }

    public void setDurezatotal(double durezatotal) {
        this.durezatotal = durezatotal;
    }

    public double getConductividad() {
        return conductividad;
    }

    public void setConductividad(double conductividad) {
        this.conductividad = conductividad;
    }

    public double getSolidosdisueltos() {
        return solidosdisueltos;
    }

    public void setSolidosdisueltos(Double solidosdisueltos) {
        this.solidosdisueltos = solidosdisueltos;
    }

    public String getOlor() {
        return olor;
    }

    public void setOlor(String olor) {
        this.olor = olor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHierro() {
        return hierro;
    }

    public void setHierro(String hierro) {
        this.hierro = hierro;
    }

    public String getNitratos() {
        return nitratos;
    }

    public void setNitratos(String nitratos) {
        this.nitratos = nitratos;
    }

    public String getNitritos() {
        return nitritos;
    }

    public void setNitritos(String nitritos) {
        this.nitritos = nitritos;
    }

    public String getSulfatos() {
        return sulfatos;
    }

    public void setSulfatos(String sulfatos) {
        this.sulfatos = sulfatos;
    }

    
    public int getIdmuestras() {
        return idmuestras;
    }

    public void setIdmuestras(int idmuestras) {
        this.idmuestras = idmuestras;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getGermenes() {
        return germenes;
    }

    public void setGermenes(String germenes) {
        this.germenes = germenes;
    }

    public String getColiformesTotales() {
        return coliformesTotales;
    }

    public void setColiformesTotales(String coliformesTotales) {
        this.coliformesTotales = coliformesTotales;
    }

    public String getEscherichia() {
        return escherichia;
    }

    public void setEscherichia(String escherichia) {
        this.escherichia = escherichia;
    }

    public String getPseudomona() {
        return pseudomona;
    }

    public void setPseudomona(String pseudomona) {
        this.pseudomona = pseudomona;
    }
    
    public void setResultadoFQ(String[] valores){
        setIdmuestras(Integer.parseInt(valores[0]));
        setPh(Double.parseDouble(valores[1]));
        setClorototal(Double.parseDouble(valores[2]));
        setOlor(valores[3]);
        setColor(valores[4]);
        setTurbidez(valores[5]);
        setAlcalinidad(Double.parseDouble(valores[6]));
        setDurezatotal(Double.parseDouble(valores[7]));
        setConductividad(Double.parseDouble(valores[8]));
        setSolidosdisueltos(Double.parseDouble(valores[9]));
        setHierro(valores[10]);
        setNitratos(valores[11]);
        setNitritos(valores[12]);
        setSulfatos(valores[13]);
        setConclusion(valores[14]);
        setObservaciones(valores[15]);
    }
    
    public void setResultadoMB(String[] valores){
        setIdmuestras(Integer.parseInt(valores[0]));
        setGermenes(valores[1]);
        setColiformesTotales(valores[2]);
        setColiformesFecales(valores[3]);
        setEscherichia(valores[4]);
        setPseudomona(valores[5]);
        setObservaciones(valores[6]);
        setPh(Double.parseDouble(valores[7]));
        setClorototal(Double.parseDouble(valores[8]));
        setCaracteresOrgasnolepticos(valores[9]);
        setMohos(valores[10]);
        setShigella(valores[11]);
    }
}

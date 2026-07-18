package org.ignaciorodriguez.modelo;

import java.sql.Date;

public class Muestra {

    String procedencia, solicitante, numeroEstablecimiento, realizadoPor, lote,
            caracteres, identificacion, tipo, lugarMuestreo, tipoAgua;
    double porcentajeCloro, ph, costeTotal;
    int pago, idcliente, factura, id;
    boolean guardar;
    Date fechaMuestreo, fechaAnalisis, fechaVencimiento, fechaElaboracion;

    public String getTipoAgua() {
        return tipoAgua;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

    public void setTipoAgua(String tipoAgua) {
        this.tipoAgua = tipoAgua;
    }
    
    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }
    
    public String getLugarMuestreo() {
        return lugarMuestreo;
    }

    public void setLugarMuestreo(String lugarMuestreo) {
        this.lugarMuestreo = lugarMuestreo;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public int getFactura() {
        return factura;
    }

    public void setFactura(int factura) {
        this.factura = factura;
    }

    public String getCaracteres() {
        return caracteres;
    }

    public void setCaracteres(String caracteres) {
        this.caracteres = caracteres;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public double getCosteTotal() {
        return costeTotal;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public void setCosteTotal(double costeTotal) {
        this.costeTotal = costeTotal;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getNumeroEstablecimiento() {
        return numeroEstablecimiento;
    }

    public void setNumeroEstablecimiento(String numeroEstablecimiento) {
        this.numeroEstablecimiento = numeroEstablecimiento;
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    public double getPorcentajeCloro() {
        return porcentajeCloro;
    }

    public void setPorcentajeCloro(double porcentajeCloro) {
        this.porcentajeCloro = porcentajeCloro;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public Date getFechaMuestreo() {
        return fechaMuestreo;
    }

    public void setFechaMuestreo(Date fechaMuestreo) {
        this.fechaMuestreo = fechaMuestreo;
    }

    public Date getFechaAnalisis() {
        return fechaAnalisis;
    }

    public void setFechaAnalisis(Date fechaAnalisis) {
        this.fechaAnalisis = fechaAnalisis;
    }

    @Override
    public String toString() {
        return "Muestra{" + "procedencia=" + procedencia + ", solicitante=" + solicitante + ", numeroEstablecimiento=" + numeroEstablecimiento + ", realizadoPor=" + realizadoPor + ", lote=" + lote + ", caracteres=" + caracteres + ", identificacion=" + identificacion + ", tipo=" + tipo + ", lugarMuestreo=" + lugarMuestreo + ", tipoAgua=" + tipoAgua + ", porcentajeCloro=" + porcentajeCloro + ", ph=" + ph + ", costeTotal=" + costeTotal + ", pago=" + pago + ", idcliente=" + idcliente + ", factura=" + factura + ", id=" + id + ", fechaMuestreo=" + fechaMuestreo + ", fechaAnalisis=" + fechaAnalisis + ", fechaVencimiento=" + fechaVencimiento + ", fechaElaboracion=" + fechaElaboracion + '}';
    }
    

}

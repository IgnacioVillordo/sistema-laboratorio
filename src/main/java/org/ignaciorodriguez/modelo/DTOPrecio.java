/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ignaciorodriguez.modelo;

public class DTOPrecio {

    private String nombre;
    private Double precio;
    private boolean seleccionado;
    private int idprecio;

    public DTOPrecio() {
    }

    public DTOPrecio(String nombre, Double precio) {
        this.precio = precio;
        this.nombre = nombre;
        this.seleccionado = false;
    }
    
    public DTOPrecio(String nombre, Double precio, int idprecios) {
        this.precio = precio;
        this.nombre = nombre;
        this.seleccionado = false;
    }

    @Override
    public String toString() {
        return "DTOPrecio{" + "nombre=" + nombre + ", precio=" + precio + ", seleccionado=" + seleccionado + '}';
    }

    public int getIdprecio() {
        return idprecio;
    }

    public void setIdprecio(int idprecio) {
        this.idprecio = idprecio;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

}

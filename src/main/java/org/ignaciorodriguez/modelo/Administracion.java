package org.ignaciorodriguez.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "administracion")
public class Administracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idadministracion;
    Long idmuestras;
    Double precioTotal;
    Boolean pago;
    Boolean factura;
    Boolean entregado;
    Boolean analizado;
    Boolean seleccionado;
    LocalDateTime entrada;
    Boolean borrado;
    Boolean seleccionadoVencimiento;
}

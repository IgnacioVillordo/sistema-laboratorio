package org.ignaciorodriguez.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "vistamanual")
public class VistaManual {

    @Id
    private Long idmuestras;
    private String muestraAnalizada;
    private LocalDate fechaElaboracion;
    private String tipo;
    private String numeroEstablecimiento;
    private LocalDate fechaVencimiento;
    private String solicitante;
    private LocalDate fechaMuestreo;
    private LocalDate fechaAnalisis;
    private String realizadoPor;
    private String descripcionMuestra;
    private String lote;
    private String observaciones;

    private String determinacion1;
    private String determinacion2;
    private String determinacion3;
    private String determinacion4;
    private String determinacion5;
    private String determinacion6;
    private String determinacion7;
    private String determinacion8;
    private String determinacion9;
    private String determinacion10;
    private String determinacion11;
    private String determinacion12;
    private String determinacion13;
    private String determinacion14;
    private String determinacion15;
    private String determinacion16;
    private String determinacion17;
    private String determinacion18;
    private String determinacion19;
    private String determinacion20;
    private String determinacion21;
    private String determinacion22;
    private String determinacion23;
    private String determinacion24;
    private String determinacion25;
    private String determinacion26;
    private String determinacion27;
    private String determinacion28;
    private String determinacion29;
    private String determinacion30;
    private String determinacion31;
    private String determinacion32;
    private String determinacion33;
    private String determinacion34;

    private String recuentoObtenido1;
    private String recuentoObtenido2;
    private String recuentoObtenido3;
    private String recuentoObtenido4;
    private String recuentoObtenido5;
    private String recuentoObtenido6;
    private String recuentoObtenido7;
    private String recuentoObtenido8;
    private String recuentoObtenido9;
    private String recuentoObtenido10;
    private String recuentoObtenido11;
    private String recuentoObtenido12;
    private String recuentoObtenido13;
    private String recuentoObtenido14;
    private String recuentoObtenido15;
    private String recuentoObtenido16;
    private String recuentoObtenido17;
    private String recuentoObtenido18;
    private String recuentoObtenido19;
    private String recuentoObtenido20;
    private String recuentoObtenido21;
    private String recuentoObtenido22;
    private String recuentoObtenido23;
    private String recuentoObtenido24;
    private String recuentoObtenido25;
    private String recuentoObtenido26;
    private String recuentoObtenido27;
    private String recuentoObtenido28;
    private String recuentoObtenido29;
    private String recuentoObtenido30;
    private String recuentoObtenido31;
    private String recuentoObtenido32;
    private String recuentoObtenido33;
    private String recuentoObtenido34;

    private String recuentoNormal1;
    private String recuentoNormal2;
    private String recuentoNormal3;
    private String recuentoNormal4;
    private String recuentoNormal5;
    private String recuentoNormal6;
    private String recuentoNormal7;
    private String recuentoNormal8;
    private String recuentoNormal9;
    private String recuentoNormal10;
    private String recuentoNormal11;
    private String recuentoNormal12;
    private String recuentoNormal13;
    private String recuentoNormal14;
    private String recuentoNormal15;
    private String recuentoNormal16;
    private String recuentoNormal17;
    private String recuentoNormal18;
    private String recuentoNormal19;
    private String recuentoNormal20;
    private String recuentoNormal21;
    private String recuentoNormal22;
    private String recuentoNormal23;
    private String recuentoNormal24;
    private String recuentoNormal25;
    private String recuentoNormal26;
    private String recuentoNormal27;
    private String recuentoNormal28;
    private String recuentoNormal29;
    private String recuentoNormal30;
    private String recuentoNormal31;
    private String recuentoNormal32;
    private String recuentoNormal33;
    private String recuentoNormal34;

    private String metodo1;
    private String metodo2;
    private String metodo3;
    private String metodo4;
    private String metodo5;
    private String metodo6;
    private String metodo7;
    private String metodo8;
    private String metodo9;
    private String metodo10;
    private String metodo11;
    private String metodo12;
    private String metodo13;
    private String metodo14;
    private String metodo15;
    private String metodo16;
    private String metodo17;
    private String metodo18;
    private String metodo19;
    private String metodo20;
    private String metodo21;
    private String metodo22;
    private String metodo23;
    private String metodo24;
    private String metodo25;
    private String metodo26;
    private String metodo27;
    private String metodo28;
    private String metodo29;
    private String metodo30;
    private String metodo31;
    private String metodo32;
    private String metodo33;
    private String metodo34;

    private String titulo;
    private String conclusion;
    private String mostrar;
}

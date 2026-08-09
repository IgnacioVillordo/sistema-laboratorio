package org.ignaciorodriguez.modelo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Efluentes {
    private Date fechaMuestreo;
    private double ph;
    private String dqo;
    private String dbo;
    private String solidos10;
    private String solidos120;
    private String detergentes;
    private String grasas;
    private String fosforo;
    private String nitrogeno;
    private String sustancias;
    private String coliformesTotales;
    private String escherichia;
    private String conductividad;
    private String hidrocarburos;
    private String nitratos;
    private String cloro;
    private String sulfuros;
}

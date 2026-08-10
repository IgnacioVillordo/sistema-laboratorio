package org.ignaciorodriguez.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GananciasDto {
    private Double ganancias;
    private LocalDate fecha;

}

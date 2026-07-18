use laboratorio; 
alter VIEW `laboratorio`.`vistatabla` AS
    SELECT 
        `m`.`idmuestras` AS `idmuestras`,
        `laboratorio`.`v`.`procedencia` AS `procedencia`,
        `m`.`solicitante` AS `solicitante`,
        `m`.`numeroEstablecimiento` AS `numeroEstablecimiento`,
        `m`.`fechaMuestreo` AS `fechaMuestreo`,
        `m`.`fechaAnalisis` AS `fechaAnalisis`,
        `m`.`realizadoPor` AS `realizadoPor`,
        `a`.`entrada` AS `entrada`,
        `a`.`pago` AS `pago`,
        `a`.`factura` AS `factura`,
        IF((`a`.`borrado` = 1),
            '----------ANULADO----------',
            `m`.`tipo`) AS `tipo`,
        `m`.`identificacion` AS `identificaciones`
    FROM
        ((`laboratorio`.`vistaprocedencia` `v`
        JOIN `laboratorio`.`muestras` `m`)
        JOIN `laboratorio`.`administracion` `a` ON (((`m`.`idcliente` = `laboratorio`.`v`.`idcliente`)
            AND (`a`.`idmuestras` = `m`.`idmuestras`)
            AND (`a`.`entregado` = 0))))
    ORDER BY `m`.`idmuestras` DESC;

ALTER TABLE `laboratorio`.`determinaciones` 
ADD COLUMN `antimonio` TEXT NULL AFTER `colorantesnaturales`,
ADD COLUMN `asbesto` TEXT NULL AFTER `antimonio`,
ADD COLUMN `azucaresInvertidos` TEXT NULL AFTER `asbesto`,
ADD COLUMN `azucaresReductores` TEXT NULL AFTER `azucaresInvertidos`,
ADD COLUMN `cenizasInsolublesAcido` TEXT NULL AFTER `azucaresReductores`,
ADD COLUMN `cenizasInsolublesAgua` TEXT NULL AFTER `cenizasInsolublesAcido`;

ALTER TABLE `laboratorio`.`determinacionesmetodo` 
ADD COLUMN `antimonioMetodo` TEXT NULL AFTER `colorantesnaturalesMetodo`,
ADD COLUMN `asbestoMetodo` TEXT NULL AFTER `antimonioMetodo`,
ADD COLUMN `azucaresInvertidosMetodo` TEXT NULL AFTER `asbestoMetodo`,
ADD COLUMN `azucaresReductoresMetodo` TEXT NULL AFTER `azucaresInvertidosMetodo`,
ADD COLUMN `cenizasInsolublesAcidoMetodo` TEXT NULL AFTER `azucaresReductoresMetodo`,
ADD COLUMN `cenizasInsolublesAguaMetodo` TEXT NULL AFTER `cenizasInsolublesAcidoMetodo`;

alter VIEW `laboratorio`.`vistambagua` AS
    SELECT 
        CONCAT(IF((`laboratorio`.`muestras`.`aguaTipo` = ' '),
                    '',
                    CONCAT(`laboratorio`.`muestras`.`aguaTipo`,
                            '. ')),
                'Muestra identificada como "',
                `laboratorio`.`muestras`.`identificacion`,
                '". ',
                `laboratorio`.`vistatabla2`.`procedencia`) AS `procedencia`,
        `laboratorio`.`muestras`.`idmuestras` AS `vistatabla_idmuestras`,
        `laboratorio`.`muestras`.`solicitante` AS `vistatabla_solicitante`,
        `laboratorio`.`muestras`.`numeroEstablecimiento` AS `vistatabla_numeroEstablecimiento`,
        `laboratorio`.`mbagua`.`clorototal` AS `vistatabla_porcentajeTotalCloro`,
        `laboratorio`.`mbagua`.`ph` AS `vistatabla_ph`,
        `laboratorio`.`muestras`.`fechaMuestreo` AS `vistatabla_fechaMuestreo`,
        `laboratorio`.`muestras`.`fechaAnalisis` AS `vistatabla_fechaAnalisis`,
        `laboratorio`.`muestras`.`realizadoPor` AS `vistatabla_realizadoPor`,
        `laboratorio`.`muestras`.`tipo` AS `vistatabla_tipo`,
        `laboratorio`.`muestras`.`observaciones` AS `observaciones`,
        `laboratorio`.`vistavencimientos`.`fechaVencimiento` AS `fechaVencimiento`,
        `laboratorio`.`mbagua`.`germenes` AS `germenes`,
        `laboratorio`.`mbagua`.`coliformesTotales` AS `coliformesTotales`,
        `laboratorio`.`mbagua`.`coliformesFecales` AS `coliformesFecales`,
        `laboratorio`.`mbagua`.`escherichia` AS `escherichia`,
        `laboratorio`.`mbagua`.`pseudomona` AS `pseudomona`,
        `laboratorio`.`mbagua`.`caracteresOrganolepticos` AS `caracteresOrganolepticos`,
        `laboratorio`.`mbagua`.`cloroLibre` AS `cloroLibre`,
        `laboratorio`.`mbagua`.`staphilococos` AS `staphilococos`,
        `laboratorio`.`mbagua`.`streptococos` AS `streptococos`,
        `laboratorio`.`muestras`.`conclusion` AS `conclusion`,
        `laboratorio`.`mbagua`.`vencimiento` AS `vencimiento`,
        `laboratorio`.`mbagua`.`mohos` AS `mohos`,
        `laboratorio`.`muestras`.`ponerFechaVencimiento` AS `ponerFechaVencimiento`
    FROM
        (`laboratorio`.`vistatabla2`
        JOIN (`laboratorio`.`mbagua`
        JOIN (`laboratorio`.`muestras`
        JOIN `laboratorio`.`vistavencimientos`)))
    WHERE
        ((`laboratorio`.`muestras`.`idmuestras` = `laboratorio`.`vistavencimientos`.`idmuestras`)
            AND (`laboratorio`.`vistavencimientos`.`idmuestras` = `laboratorio`.`vistatabla2`.`idmuestras`)
            AND (`laboratorio`.`vistatabla2`.`idmuestras` = `laboratorio`.`mbagua`.`idmuestras`));


alter VIEW `laboratorio`.`vistambchocolates` AS
    SELECT 
        `m`.`idmuestras` AS `idmuestras`,
        `m`.`tipo` AS `tipo`,
        `m`.`numeroEstablecimiento` AS `numeroEstablecimiento`,
        CONCAT('Chocolates. “Del Turista”. Muestra identificada como "',
                m.identificacion,
                '".') AS muestraAnalizada,
        m.fechaElaboracion AS fechaElaboracion,
        m.fechaVencimiento AS fechaVencimiento,
        m.solicitante AS solicitante,
        m.fechaMuestreo AS fechaMuestreo,
        m.fechaAnalisis AS fechaAnalisis,
        m.realizadoPor AS realizadoPor,
        mb.germenes AS germenes,
        mb.coliformesTotales AS coliformesTotales,
        mb.coliformesFecales AS coliformesFecales,
        mb.escherichia AS escherichia,
        mb.mohos AS mohos,
        m.observaciones AS observaciones,
        mb.staphilococos AS staphilococos,
        mb.salmonella AS salmonella,
        m.conclusion AS conclusion
    FROM
        ((laboratorio.mbchocolates mb
        JOIN laboratorio.muestras m)
        JOIN laboratorio.administracion a)
    WHERE
        ((m.idmuestras = mb.idmuestras)
            AND (m.idmuestras = a.idmuestras));
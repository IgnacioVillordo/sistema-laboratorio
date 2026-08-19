package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.FqAgua;

import java.util.List;
import java.util.Optional;

public interface FqAguaService {

    FqAgua guardarFqAgua(FqAgua fqAgua);
    FqAgua editarFqAgua(FqAgua fqAgua, Long id);
    Optional<FqAgua>  recuperarFqAgua(Long id);
    List<FqAgua> recuperarFqAguas();
}

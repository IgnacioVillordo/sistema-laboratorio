package org.ignaciorodriguez.repository;

import org.ignaciorodriguez.modelo.Archivo;
import org.ignaciorodriguez.modelo.Conexion;
import org.ignaciorodriguez.service.ArchivoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
}

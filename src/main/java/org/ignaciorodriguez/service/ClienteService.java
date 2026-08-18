package org.ignaciorodriguez.service;

import org.ignaciorodriguez.modelo.Cliente;
import org.ignaciorodriguez.modelo.VistaProcedencia;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente guardarCliente(Cliente cliente);
    Boolean reactivarCliente(Long id);
    Optional<Cliente> obtenerCliente(Long id);
    Boolean borrarCliente(Long id);
    List<Cliente> recuperarClientes();
    Optional<Cliente> editarCliente(Cliente cliente, Long id);
    List<VistaProcedencia> recuperarClientesAnulados(Boolean activo);
    Optional<VistaProcedencia> recuperarIdClientePorProcedencia(String procedencia);
}

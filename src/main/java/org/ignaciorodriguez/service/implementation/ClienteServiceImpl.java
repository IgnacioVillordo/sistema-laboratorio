package org.ignaciorodriguez.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.ignaciorodriguez.modelo.Cliente;
import org.ignaciorodriguez.modelo.VistaProcedencia;
import org.ignaciorodriguez.repository.ClienteRepository;
import org.ignaciorodriguez.repository.VistaProcedenciaRepository;
import org.ignaciorodriguez.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    VistaProcedenciaRepository vistaProcedenciaRepository;

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Boolean reactivarCliente(Long id) {
        return clienteRepository.reactivarCliente(id);
    }

    @Override
    public Optional<Cliente> obtenerCliente(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Boolean borrarCliente(Long id) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setActivo(false);
            clienteRepository.save(cliente);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Cliente> recuperarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> editarCliente(Cliente cliente, Long id) {
        return clienteRepository.findById(id).map(clienteBd -> {
            clienteBd.setId(id);
            return clienteRepository.save(cliente);
        });
    }

    @Override
    public List<VistaProcedencia> recuperarClientesAnulados(Boolean activo) {
        return vistaProcedenciaRepository.findAllByActivo(activo);
    }

    @Override
    public Optional<VistaProcedencia> recuperarIdClientePorProcedencia(String procedencia) {
        return vistaProcedenciaRepository.findByProcedencia(procedencia);
    }
}

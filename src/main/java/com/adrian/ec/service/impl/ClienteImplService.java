package com.adrian.ec.service.impl;

import com.adrian.ec.model.dao.ClienteDao;
import com.adrian.ec.model.dto.ClienteDto;
import com.adrian.ec.model.entity.Cliente;
import com.adrian.ec.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteImplService implements IClienteService {

    @Autowired //Inyección de Dependencia
    private ClienteDao clienteDao;

    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    // Transactional da soporte de Spring, para no tener que inicializar
    // la transacción con begin() y cerrarla con commit()
    @Transactional
    @Override
    public Cliente save(ClienteDto clienteDto) {
        Cliente cliente = Cliente.builder()
                .idCliente( clienteDto.getIdCliente() )
                .nombre( clienteDto.getNombre() )
                .apellido( clienteDto.getApellido() )
                .correo( clienteDto.getCorreo() )
                .fechaRegistro( clienteDto.getFechaRegistro() )
                .build();
        return clienteDao.save(cliente);
    }

    @Transactional(readOnly = true) //Solo es de tipo lectura(consulta)
    @Override
    public Cliente findById(Integer id) {
        // En caso no se encuentre, retornará null
        return clienteDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Cliente cliente) {
        clienteDao.delete(cliente);
    }

    @Override
    public boolean existsById(Integer id) {
        return clienteDao.existsById(id);
    }


}

package com.adrian.ec.service;

import com.adrian.ec.model.dto.ClienteDto;
import com.adrian.ec.model.entity.Cliente;

import java.util.List;

public interface IClienteService {

    List<Cliente> findAll();

    // Cuando se envia el ID en save() CrudRepository reconoce que
    // se quiere actualizar y lo hace por detrás
    Cliente save(ClienteDto cliente);

    Cliente findById(Integer id);

    void delete(Cliente cliente);

    boolean existsById(Integer id);

}

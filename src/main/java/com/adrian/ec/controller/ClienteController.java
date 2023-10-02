package com.adrian.ec.controller;

import com.adrian.ec.model.dto.ClienteDto;
import com.adrian.ec.model.entity.Cliente;
import com.adrian.ec.model.payload.MessageResponse;
import com.adrian.ec.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Identifica esta clase como CONTROLADOR
@RestController //Se usa para crear servicios web RESTful usando spring MVC
// Hace que este controlador sea utilizado como recurso bajo una URL para que trabaje
// con los metodos que tiene dentro.
@RequestMapping ("/api/v1")
public class ClienteController {

    @Autowired
    private IClienteService clienteService; // ICliente esta implmentando en "ClienteImpl(Service)"


    @GetMapping("clientes")
    public ResponseEntity<?> showAll(){
        List<Cliente> getList = clienteService.findAll();

        // Si la lista esta vacia
        if (getList.isEmpty()) {
            return new ResponseEntity<>(
                    // MessageResponse es mi PAYLOAD personalizado
                    MessageResponse.builder()
                            .message("No hay registros.")
                            .object(null)
                            .build()
                    , HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                // MessageResponse es mi PAYLOAD personalizado
                MessageResponse.builder()
                        .message("")
                        .object(getList)
                        .build()
                , HttpStatus.OK
        );
    }

    @PostMapping("cliente")
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto){ //@RequestBody transforma el formato JSON en objeto Cliente
        Cliente clienteSave = null;
        try {
            clienteSave = clienteService.save(clienteDto);
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("Guardado correctamente")
                            .object(
                                    // Utilizamos ClienteDTO como espejo y como un constructor personalizado
                                    ClienteDto.builder()
                                    .idCliente( clienteSave.getIdCliente() )
                                    .nombre( clienteSave.getNombre() )
                                    .apellido( clienteSave.getApellido() )
                                    .correo( clienteSave.getCorreo() )
                                    .fechaRegistro( clienteSave.getFechaRegistro() )
                                    .build()
                            )
                            .build()
                    , HttpStatus.CREATED
            );
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    // MessageResponse es mi PAYLOAD personalizado
                    MessageResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }

    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto, @PathVariable Integer id){
        Cliente clienteUpdate = null;
        try {

            // Validamos que el id exista en los registros antes de actualizar
            if (clienteService.existsById(id)) {
                clienteDto.setIdCliente(id); // En caso envién un ID por el BODY y no por la URI
                clienteUpdate = clienteService.save(clienteDto);
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .message("Actualizado correctamente")
                                .object(
                                        // Utilizamos ClienteDTO como espejo y como un constructor personalizado
                                        ClienteDto.builder()
                                                .idCliente( clienteUpdate.getIdCliente() )
                                                .nombre( clienteUpdate.getNombre() )
                                                .apellido( clienteUpdate.getApellido() )
                                                .correo( clienteUpdate.getCorreo() )
                                                .fechaRegistro( clienteUpdate.getFechaRegistro() )
                                                .build()
                                )
                                .build()
                        , HttpStatus.CREATED
                );
            } else {
                return new ResponseEntity<>(
                        // MessageResponse es mi PAYLOAD personalizado
                        MessageResponse.builder()
                                .message("El registro que intenta actualizar no existe.")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND
                );
            }
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    // MessageResponse es mi PAYLOAD personalizado
                    MessageResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }

    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        // ResponseEntity maneja la respuesta HTTP incluyendo el BODY, HEADER Y STATUS CODE.
        try {
            Cliente clienteDelete = clienteService.findById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>( clienteDelete, HttpStatus.NO_CONTENT );

        } catch (DataAccessException exDt){

            return new ResponseEntity<>(
                    // MessageResponse es mi PAYLOAD personalizado
                    MessageResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        Cliente cliente =  clienteService.findById(id);

        if (cliente == null) {
            return new ResponseEntity<>(
                    // MessageResponse es mi PAYLOAD personalizado
                    MessageResponse.builder()
                            .message("El registro que intenta buscar no existe.")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                // MessageResponse es mi PAYLOAD personalizado
                MessageResponse.builder()
                        .message("")
                        .object(
                                ClienteDto.builder()
                                .idCliente( cliente.getIdCliente() )
                                .nombre( cliente.getNombre() )
                                .apellido( cliente.getApellido() )
                                .correo( cliente.getCorreo() )
                                .fechaRegistro( cliente.getFechaRegistro() )
                                .build()
                        )
                        .build()
                , HttpStatus.OK
        );
    }

}

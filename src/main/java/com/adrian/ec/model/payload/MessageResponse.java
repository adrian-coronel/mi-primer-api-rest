package com.adrian.ec.model.payload;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MessageResponse implements Serializable { // SERIALIZABLE -> proceso de convertir un objeto en secuencia de bytes


    private String message;
    private Object object;

}

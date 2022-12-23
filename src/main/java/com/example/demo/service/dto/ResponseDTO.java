package com.example.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO implements Serializable {

    private String status;
    private String message;
    private Object data;

    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }
}

package com.zup.academy.global.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    private HttpStatus status;

    public CustomException(HttpStatus httpStatus, String message){
        super(message);
        this.status = httpStatus;
    }

}

package com.zup.academy.global.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    private HttpStatus status;

    public CustomException(HttpStatus httpStatus, String message){
        super(message);
        this.status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static CustomException unprocessable(String message){
        return new CustomException(HttpStatus.UNPROCESSABLE_ENTITY,message);
    }

    public static CustomException notFound(String message){
        return new CustomException(HttpStatus.NOT_FOUND,message);
    }

}

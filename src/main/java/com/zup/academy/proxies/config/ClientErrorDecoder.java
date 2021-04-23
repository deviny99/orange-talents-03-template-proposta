package com.zup.academy.proxies.config;

import com.zup.academy.global.exception.CustomException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class ClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        if(400 == response.status()){
            return CustomException.unprocessable("Solicitante com restrição");
        }

        if(422 == response.status()){
            return CustomException.unprocessable("Solicitante com restrição");
        }

        if(403 == response.status()){
            return CustomException.unprocessable("");
        }

        if(500 == response.status()){
            return CustomException.unprocessable("");
        }

        if(404 == response.status()){
            return CustomException.notFound("");
        }

        return defaultErrorDecoder.decode(s,response);
    }
}

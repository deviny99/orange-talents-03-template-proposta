package com.zup.academy.proxies.config;

import com.zup.academy.global.exception.CustomException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class ClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        System.out.println(response.headers());

        if(400 == response.status()){
            return CustomException.unprocessable("Solicitante com restrição");
        }

        if(422 == response.status()){
            return CustomException.unprocessable("Solicitante com restrição");
        }

        if(403 == response.status()){
            return CustomException.notFound(response.toString());
        }

        if(500 == response.status()){
            return CustomException.badRequest(response.toString());
        }

        if(404 == response.status()){
            return CustomException.notFound(response.toString());
        }

        return defaultErrorDecoder.decode(s,response);
    }
}

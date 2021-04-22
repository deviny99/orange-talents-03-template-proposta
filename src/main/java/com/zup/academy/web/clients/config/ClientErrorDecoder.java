package com.zup.academy.web.clients.config;

import com.zup.academy.global.exception.CustomException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class ClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        if(422 == response.status()){
            return CustomException.unprocessable("Solicitante com restrição");
        }

        return defaultErrorDecoder.decode(s,response);
    }
}

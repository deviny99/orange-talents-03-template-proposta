package com.zup.academy.proposta;

import com.zup.academy.proposta.dto.DocumentoRequest;
import com.zup.academy.proposta.dto.PropostaRequest;
import org.springframework.context.annotation.Profile;
import java.math.BigDecimal;

@Profile({"integracao-test","test"})
public class PropostaDataBuilder {


    public static PropostaRequest propostaRequestValido(DocumentoRequest documentoRequest){

        return new PropostaRequest(
                "teste@teste.com",
                "teste integrado",
                "rua da integração",
                new BigDecimal(5000),
                documentoRequest);
    }


    public static PropostaRequest propostaRequestCpfValido(){
       return propostaRequestValido(new DocumentoRequest("061.701.750-60"));
    }

    public static PropostaRequest propostaRequestCnpjValido(){
        return propostaRequestValido(new DocumentoRequest("77.357.690/0001-65"));
    }

    public static PropostaRequest propostaRequestCpfInvalido(){
        return propostaRequestValido(new DocumentoRequest("000.000.000-00"));
    }
}

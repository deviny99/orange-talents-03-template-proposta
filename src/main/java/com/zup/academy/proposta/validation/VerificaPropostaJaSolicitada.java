package com.zup.academy.proposta.validation;

import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proposta.dto.PropostaRequest;
import com.zup.academy.proposta.repository.PropostaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VerificaPropostaJaSolicitada implements Validator {

    private final PropostaRespository propostaRespository;

    @Autowired
    public VerificaPropostaJaSolicitada(PropostaRespository propostaRespository){
        this.propostaRespository = propostaRespository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PropostaRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        PropostaRequest propostaRequest = (PropostaRequest) target;
        this.propostaRespository.findByDocumento(propostaRequest.getNumeroRegistro()).ifPresent(it ->{
            throw CustomException.unprocessable("Já foi cadastrado uma proposta para esse solicitante: "+propostaRequest.getNumeroRegistro());
        });
    }
}

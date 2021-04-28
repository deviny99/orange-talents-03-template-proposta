package com.zup.academy.cartao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.academy.cartao.domain.StatusBloqueioCartao;

public class BloqueioCartaoResponse {

    @JsonProperty("sistemaResponsavel")
    private String sistemaResponsavel;

    public BloqueioCartaoResponse(String sistemaResponsavel){
        this.sistemaResponsavel = sistemaResponsavel;
    }


    public StatusBloqueioCartao toResponse(){
        return (this.sistemaResponsavel.equals(StatusBloqueioCartao.BLOQUEADO.toString()))
                ? StatusBloqueioCartao.BLOQUEADO
                : StatusBloqueioCartao.NAO_BLOQUEADO;
    }

}

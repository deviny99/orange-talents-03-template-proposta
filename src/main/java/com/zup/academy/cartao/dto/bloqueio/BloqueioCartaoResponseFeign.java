package com.zup.academy.cartao.dto.bloqueio;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.academy.cartao.domain.StatusBloqueioCartao;

public class BloqueioCartaoResponseFeign {

    @JsonProperty("resultado")
    private String resultado;

    @Deprecated
    public BloqueioCartaoResponseFeign(){}

    public BloqueioCartaoResponseFeign(String sistemaResponsavel){
        this.resultado = sistemaResponsavel;
    }

    public StatusBloqueioCartao toResponse(){
        return (this.resultado.equals(StatusBloqueioCartao.BLOQUEADO.toString()))
                ? StatusBloqueioCartao.BLOQUEADO
                : StatusBloqueioCartao.NAO_BLOQUEADO;
    }

}

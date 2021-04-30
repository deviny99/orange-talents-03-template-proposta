package com.zup.academy.cartao.dto.viagem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.academy.cartao.domain.StatusNotificacaoViagem;

public class NotificacaoViagemResponseFeign {

    @JsonProperty("resultado")
    private String resultado;

    public NotificacaoViagemResponseFeign(){}

    public NotificacaoViagemResponseFeign(String resultado) {
        this.resultado = resultado;
    }

    public StatusNotificacaoViagem toResponse(){

        return this.resultado.equals(StatusNotificacaoViagem.CRIADO.toString())
                ? StatusNotificacaoViagem.CRIADO
                : StatusNotificacaoViagem.NAO_CRIADO;
    }

}

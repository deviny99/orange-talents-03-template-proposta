package com.zup.academy.cartao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.domain.NotificacaoViagem;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class NotificacaoViagemRequest {

    @NotBlank
    @JsonProperty("destino")
    private String destino;
    @NotBlank
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonProperty("dtTermino")
    private LocalDate dtTermino;

    public NotificacaoViagemRequest(String destino, LocalDate dtTermino) {
        this.destino = destino;
        this.dtTermino = dtTermino;
    }

    public NotificacaoViagem toModel(Long id, Cartao cartao, String userAgent, String ipAddress){
        return new NotificacaoViagem(id,cartao,userAgent,ipAddress,this.destino,this.dtTermino);
    }
}

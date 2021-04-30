package com.zup.academy.cartao.dto.viagem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.domain.NotificacaoViagem;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NotificacaoViagemRequestFeign {

    @NotBlank
    @JsonProperty("destino")
    private String destino;
    @NotNull
    @FutureOrPresent
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("validoAte")
    private LocalDate validoAte;

    public NotificacaoViagemRequestFeign(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public NotificacaoViagem toModel(Long id, Cartao cartao, String userAgent, String ipAddress){
        return new NotificacaoViagem(id,cartao,userAgent,ipAddress,this.destino,this.validoAte);
    }
}

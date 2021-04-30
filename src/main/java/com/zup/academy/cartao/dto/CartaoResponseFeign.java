package com.zup.academy.cartao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.academy.cartao.domain.Cartao;
import java.time.LocalDateTime;

public class CartaoResponseFeign {

    @JsonProperty("id")
    private String id;
    @JsonProperty("emitidoEm")
    private LocalDateTime emitidoEm;
    @JsonProperty("titular")
    private String titular;
    @JsonProperty("idProposta")
    private String idProposta;

    public Cartao toModel(Long id){
       return new Cartao(id,this.id,this.titular,this.emitidoEm);
    }


}

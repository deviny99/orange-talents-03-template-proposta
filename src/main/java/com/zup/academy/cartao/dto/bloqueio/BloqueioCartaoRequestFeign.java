package com.zup.academy.cartao.dto.bloqueio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueioCartaoRequestFeign {

    @JsonProperty
    private String sistemaResponsavel;

     public BloqueioCartaoRequestFeign(String sistemaResponsavel){
         this.sistemaResponsavel = sistemaResponsavel;
     }

}

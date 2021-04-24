package com.zup.academy.cartao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.academy.cartao.domain.Biometria;
import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class BiometriaRequest {

    @NotBlank
    @JsonProperty("fingerPrint")
    private String fingerPrint;

    public BiometriaRequest(@JsonProperty("fingerPrint") String fingerPrint){
        this.fingerPrint = fingerPrint;
    }

    public Biometria toModel(Long id){
        byte[] decode = Base64.getDecoder().decode(this.fingerPrint);
        return new Biometria(id,decode);
    }

}

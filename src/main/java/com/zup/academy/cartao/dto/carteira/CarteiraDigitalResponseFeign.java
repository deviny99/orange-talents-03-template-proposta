package com.zup.academy.cartao.dto.carteira;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarteiraDigitalResponseFeign {

    @JsonProperty("id")
    private String id;
    @JsonProperty("resultado")
    private String resultado;


    public CarteiraDigitalResponseFeign(@JsonProperty("resultado")String resultado, @JsonProperty("id") String id){
        this.resultado = resultado;
        this.id = id;
    }


    public String getId() {
        return this.id;
    }

    public String getResultado() {
        return this.resultado;
    }

}

package com.zup.academy.cartao.dto.carteira;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraDigitalRequest {

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    public CarteiraDigitalRequest(@JsonProperty("email") String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

package com.zup.academy.cartao.dto.carteira;

import com.zup.academy.cartao.domain.CarteiraDigital;

public class CarteiraDigitalDtoFeign {


    private String email;
    private CarteiraDigital carteira;

    public CarteiraDigitalDtoFeign(String email, CarteiraDigital carteira){
        this.email = email;
        this.carteira = carteira;
    }

    public CarteiraDigital getCarteira() {
        return carteira;
    }
}

package com.zup.academy.cartao.domain;

import com.zup.academy.global.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

@DisplayName("Testa dominio de Cartao")
class CartaoTest {

    private Cartao cartao;

    @BeforeEach
    void init(){
        this.cartao = new Cartao(1l,"21313132","ciclaninho", LocalDateTime.now());
    }

    @Test
    @DisplayName("Deve vincular uma biometria")
    void deveVincularUmaBiometria()
    {
        try {
            this.cartao.vincularBiometria(new Biometria(1l,"kasdjf".getBytes()));
        }catch (Exception exception){
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Não deve vincular biometria nula")
    void deveVincularUmaBiometriaNula()
    {
        try {
            this.cartao.vincularBiometria(null);
            Assertions.fail();
        }catch (CustomException exception){
            Assertions.assertEquals(404,exception.getStatus().value());
        }
    }

}
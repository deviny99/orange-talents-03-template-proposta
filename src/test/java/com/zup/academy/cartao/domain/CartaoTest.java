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

    @Test
    @DisplayName("Deve bloquear cartão")
    void deveBloquearCartao(){
        try {
            this.cartao.bloquear();
        }catch (Exception exception){
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Não deve bloquear o cartão já bloqueado")
    void naoDeveBloquearCartaoJaBloqueado(){
        try {
            this.cartao.bloquear();
            this.cartao.bloquear();
            Assertions.fail();
        }catch (CustomException exception){
            Assertions.assertEquals(422,exception.getStatus().value());
        }
    }

    @Test
    @DisplayName("Deve desbloquear cartão")
    void deveDesbloquearCartao(){
        try {
            this.cartao.bloquear();
            this.cartao.desbloquear();
        }catch (Exception exception){
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Não deve desbloquear crtão já desbloqueado")
    void naoDeveDesbloquearCartaoJaDesbloqueado(){
        try {
            this.cartao.bloquear();
            this.cartao.desbloquear();
            this.cartao.desbloquear();
            Assertions.fail();
        }catch (CustomException exception){
            Assertions.assertEquals(422,exception.getStatus().value());
        }
    }

    @Test
    @DisplayName("Deve associar Carteira digital")
    void deveAssociarCarteira(){
        try {
            this.cartao.associarCarteira(CarteiraDigital.PAYPAL);
        }catch (Exception exception){
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Não deve associar Carteira digital já associada")
    void naoDeveAssociarCarteiraJaAssociada(){
        try {
            this.cartao.associarCarteira(CarteiraDigital.PAYPAL);
            this.cartao.associarCarteira(CarteiraDigital.PAYPAL);
            Assertions.fail();
        }catch (CustomException exception){
            Assertions.assertEquals(422,exception.getStatus().value());
        }
    }
}
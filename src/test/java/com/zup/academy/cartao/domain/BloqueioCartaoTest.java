package com.zup.academy.cartao.domain;

import com.zup.academy.global.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.UUID;

@DisplayName("Testa dominio de Bloqueio do cartao")
public class BloqueioCartaoTest {

    private BloqueioCartao bloqueioCartao;
    private Cartao cartao;

    @BeforeEach
    void init() throws UnknownHostException {
        this.bloqueioCartao = new BloqueioCartao(UUID.randomUUID(), InetAddress.getLocalHost().getHostAddress(),"testes");
        this.cartao = new Cartao(1l,"21313132","ciclaninho", LocalDateTime.now());
    }

    @Test
    @DisplayName("Deve bloquear cartão")
    void deveBloquearCartao(){

        try {
            this.bloqueioCartao.bloquearCartao(this.cartao);
        }catch (CustomException customException){
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Não deve sobscrever cartão")
    void naoDeveSobscreverCartao(){
        try {
            this.bloqueioCartao.bloquearCartao(this.cartao);
            this.bloqueioCartao.bloquearCartao(this.cartao);
            Assertions.fail();
        }catch (CustomException customException){
            Assertions.assertEquals(422,customException.getStatus().value());
        }
    }

}

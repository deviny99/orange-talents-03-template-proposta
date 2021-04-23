package com.zup.academy.proposta.domain;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.global.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@DisplayName("Testa o dominio de proposta")
public class PropostaTest {

    private Proposta proposta;

    @BeforeEach
    void init(){
        proposta = new Proposta(UUID.randomUUID(),
                "fulaninho da silva",
                "email@email",
                "rua do fulaninho",
                "440.702.110-12",
                new BigDecimal(5000));
    }


    @Test
    @DisplayName("Deve vincular cartão a proposta")
    void deveVincularCartao(){

        this.proposta.vincularCartao(new Cartao(1L,"3291912783921","fulaninho", LocalDateTime.now()));
        Assertions.assertFalse(this.proposta.getNumeroCartao().isEmpty());

   }
   @Test
   @DisplayName("Não deve vincular cartão nulo a proposta")
    void naoDeveVincularCartaoVazio(){

        try
        {
            this.proposta.vincularCartao(null);
            Assertions.fail();
        }
        catch (CustomException exception){
            Assertions.assertFalse(exception.getLocalizedMessage().isEmpty());
            Assertions.assertEquals(404,exception.getStatus().value());
        }

    }
    @Test
    @DisplayName("Não deve vincular cartão a proposta que já tenha um vinculo com um cartão")
    void naoDeveVincularCartaoJaVinculado() {
        try {
            this.proposta.vincularCartao(new Cartao(1L, "3291912783921", "fulaninho", LocalDateTime.now()));
            this.proposta.vincularCartao(new Cartao(1L, "3291912783921", "fulaninho", LocalDateTime.now()));
            Assertions.fail();
        } catch (CustomException exception) {
            Assertions.assertFalse(exception.getLocalizedMessage().isEmpty());
            Assertions.assertEquals(422,exception.getStatus().value());
        }

    }
}

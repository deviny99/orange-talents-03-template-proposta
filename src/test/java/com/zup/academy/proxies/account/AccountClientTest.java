package com.zup.academy.proxies.account;

import com.zup.academy.cartao.dto.CartaoResponse;
import com.zup.academy.proposta.domain.Proposta;
import com.zup.academy.proposta.dto.PropostaDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@DisplayName("Teste de integração com cliente de accounts")
public class AccountClientTest {

    @MockBean
    private AccountClient accountClient;

    private PropostaDto propostaDto;

    @BeforeEach
    void init(){
        Proposta proposta = new Proposta(
                UUID.randomUUID(),
                "email@email.com",
                "fulaninho",
                "440.702.110-12",
                "rua do fulano",
                new BigDecimal(3000)
        );
        this.propostaDto = new PropostaDto(proposta);
    }

    @Test
    @DisplayName("Deve associar cartão com proposta")
    void deveAssociarCartaoComProposta(){
        ResponseEntity responseEntity = ResponseEntity.ok(new CartaoResponse());
        Mockito.when(this.accountClient.addCartao(this.propostaDto)).thenReturn(responseEntity);
        Assertions.assertEquals(200,responseEntity.getStatusCodeValue());

    }


}

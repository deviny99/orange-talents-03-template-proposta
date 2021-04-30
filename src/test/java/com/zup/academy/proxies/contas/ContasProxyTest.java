package com.zup.academy.proxies.contas;

import com.zup.academy.cartao.dto.CartaoResponseFeign;
import com.zup.academy.proposta.domain.Proposta;
import com.zup.academy.proposta.dto.PropostaDtoFeign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@DisplayName("Teste de integração com cliente de accounts")
public class ContasProxyTest {

    @MockBean
    private ContasProxy contasProxy;

    private PropostaDtoFeign propostaDto;

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
        this.propostaDto = new PropostaDtoFeign(proposta);
    }

    @Test
    @DisplayName("Deve associar cartão com proposta")
    void deveAssociarCartaoComProposta(){
        CartaoResponseFeign responseEntity = new CartaoResponseFeign();
        Mockito.when(this.contasProxy.addCartao(this.propostaDto)).thenReturn(responseEntity);
        Assertions.assertNotNull(responseEntity);

    }


}

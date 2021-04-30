package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.dto.bloqueio.BloqueioCartaoResponseFeign;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.global.controller.MvcRest;
import com.zup.academy.proxies.contas.ContasProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@DisplayName("Teste dos endpoints de bloqueio do cartão")
public class BloqueioCartaoControllerTest {

    @Autowired
    private MvcRest mvcRest;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CartaoRepository cartaoRepository;

    @MockBean
    private ContasProxy contasProxy;

    private Cartao cartao;

    private final String ENDPOINT_BLOQUEIO = "/cards/bloqueio/";

    @BeforeEach
    void init(){
        this.cartao = new Cartao(1l,"2131234312","fulaninho", LocalDateTime.now());
    }

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Deve bloquear cartão")
    void deveBloquearCartao() throws Exception {

        Cartao cartao = this.cartaoRepository.save(this.cartao);

        Mockito.when(this.contasProxy.bloquearCartao(Mockito.any(),Mockito.any())).thenReturn(new BloqueioCartaoResponseFeign("BLOQUEADO"));

       var result = this.mvcRest.postEndpointWithHeaders(this.mockMvc,ENDPOINT_BLOQUEIO+cartao.getId(),"");
       Assertions.assertEquals(200,result.getResponse().getStatus());
    }

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Não deve aceitar requisição sem headers")
    void naoDeveAceitarRequisicaoSemHeaders() throws Exception {

        Mockito.when(this.contasProxy.bloquearCartao(Mockito.any(),Mockito.any())).thenReturn(new BloqueioCartaoResponseFeign("BLOQUEADO"));
        var result = this.mvcRest.postEndpoint(this.mockMvc,ENDPOINT_BLOQUEIO+"1","");
        Assertions.assertEquals(400,result.getResponse().getStatus());
    }

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Não deve bloquear cartão que não existe")
    void naoDeveBloquearCartao() throws Exception {

        Mockito.when(this.contasProxy.bloquearCartao(Mockito.any(),Mockito.any())).thenReturn(new BloqueioCartaoResponseFeign("BLOQUEADO"));

        var result = this.mvcRest.postEndpointWithHeaders(this.mockMvc,ENDPOINT_BLOQUEIO+"1","");
        Assertions.assertEquals(404,result.getResponse().getStatus());
    }


    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Não deve bloquear cartão que o sistema legado retorna como não bloqueado")
    void naoDeveBloquearCartaoNaoBloqueado() throws Exception {
        Cartao cartao = this.cartaoRepository.save(this.cartao);

        Mockito.when(this.contasProxy.bloquearCartao(Mockito.any(),Mockito.any())).thenReturn(new BloqueioCartaoResponseFeign("NAO_BLOQUEADO"));

        var result = this.mvcRest.postEndpointWithHeaders(this.mockMvc,ENDPOINT_BLOQUEIO+cartao.getId(),"");
        Assertions.assertEquals(422,result.getResponse().getStatus());
    }

}
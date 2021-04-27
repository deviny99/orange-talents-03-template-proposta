package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.repository.BloqueioRepository;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.global.controller.MvcRest;
import com.zup.academy.proxies.account.AccountClient;
import org.junit.jupiter.api.Assertions;
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
import java.util.Optional;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@DisplayName("Teste dos endpoints de bloqueio do cartão")
public class BloqueioCartaoControllerTest {

    @Autowired
    private MvcRest mvcRest;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountClient accountClient;
    @MockBean
    private CartaoRepository cartaoRepository;
    @MockBean
    private BloqueioRepository bloqueioRepository;

    private final String ENDPOINT_BLOQUEIO = "/card/bloqueio/";


    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Deve bloquear cartão")
    void deveBloquearCartao() throws Exception {
        //devemos mockar a resposta do cliente
       // Mockito.when(this.accountClient.bloquearCartao(1)).thenReturn(StatusBloqueioCartao.BLOQUEADO);
        Mockito.when(this.cartaoRepository.findById(1l)).thenReturn(Optional.of(new Cartao(1l,"2131234312","fulaninho", LocalDateTime.now())));
        Mockito.when(this.bloqueioRepository.findByCartao(new Cartao(1l,"2131234312","fulaninho", LocalDateTime.now()))).thenReturn(false);
       var result = this.mvcRest.postEndpointWithHeaders(this.mockMvc,ENDPOINT_BLOQUEIO+"1","");
       Assertions.assertEquals(200,result.getResponse().getStatus());
    }

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Não deve aceitar requisição sem headers")
    void naoDeveAceitarRequisicaoSemHeaders() throws Exception {
        //devemos mockar a resposta do cliente
        //Mockito.when(this.accountClient.bloquearCartao(1)).thenReturn(StatusBloqueioCartao.BLOQUEADO);
        var result = this.mvcRest.postEndpoint(this.mockMvc,ENDPOINT_BLOQUEIO+"1","");
        Assertions.assertEquals(400,result.getResponse().getStatus());
    }

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Não deve bloquear cartão que não existe")
    void naoDeveBloquearCartao() throws Exception {
        //devemos mockar a resposta do cliente
        // Mockito.when(this.accountClient.bloquearCartao(1)).thenReturn(StatusBloqueioCartao.BLOQUEADO);
        Mockito.when(this.cartaoRepository.findById(1l)).thenReturn(Optional.empty());
        var result = this.mvcRest.postEndpointWithHeaders(this.mockMvc,ENDPOINT_BLOQUEIO+"1","");
        Assertions.assertEquals(404,result.getResponse().getStatus());
    }

//    @Test
//    @WithMockUser
//    @Transactional
//    @Rollback
//    @DisplayName("Não deve bloquear cartão já bloqueado")
//    void naoDeveBloquearCartaoJaBloqueado() throws Exception {
//        //devemos mockar a resposta do cliente
//        var result = this.mvcRest.postEndpoint(this.mockMvc,"/card/bloqueio/1","");
//        Assertions.assertEquals(422,result.getResponse().getStatus());
//    }

}
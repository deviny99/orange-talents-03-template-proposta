package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.dto.NotificacaoViagemRequest;
import com.zup.academy.cartao.dto.NotificacaoViagemResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static com.zup.academy.global.utils.JsonMapper.asJsonString;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@DisplayName("Teste do endpoint de notifacação viagem")
public class NotificacaoViagemControllerTest {

    @Autowired
    private MvcRest mvcRest;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CartaoRepository cartaoRepository;
    @MockBean
    private AccountClient accountClient;

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Notificar banco sobre viagem para o cartão")
    void notificarUsoCartaoViagem() throws Exception {
        Cartao cartao = this.cartaoRepository.save(new Cartao(null,"1828393849","ciclaninho", LocalDateTime.now()));
        Assertions.assertNotNull(cartao);

        Mockito.when(this.accountClient.notificarBanco(cartao.getId())).thenReturn(ResponseEntity.ok(new NotificacaoViagemResponse("CRIADO")));

        var result = this.mvcRest.postEndpointWithHeaders(this.mockMvc,
                "/card/"+cartao.getId()+"/notificacao",
                asJsonString(new NotificacaoViagemRequest("Dinamarca", LocalDate.of(2999,10,10))));
        Assertions.assertEquals(201,result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getHeader("Location"));
        Assertions.assertTrue(result.getResponse().containsHeader("Location"));
    }


    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Não deve notificar banco quando a data de termino estiver no passado")
    void naoDeveNotificarUsoCartaoViagemComDataPassada() throws Exception {
        Cartao cartao = this.cartaoRepository.save(new Cartao(null,"1828393849","ciclaninho", LocalDateTime.now()));
        Assertions.assertNotNull(cartao);

        Mockito.when(this.accountClient.notificarBanco(cartao.getId())).thenReturn(ResponseEntity.ok(new NotificacaoViagemResponse("CRIADO")));

        var result = this.mvcRest.postEndpointWithHeaders(this.mockMvc,
                "/card/"+cartao.getId()+"/notificacao",
                asJsonString(new NotificacaoViagemRequest("Dinamarca", LocalDate.of(2000,10,10))));
        Assertions.assertEquals(400,result.getResponse().getStatus());
    }


    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Não deve salvar notificação quando o banco devolver \"NAO_CRIADO\" ")
    void naoDeveSalvarQuandoBancoDevolverNaoCriado() throws Exception {
        Cartao cartao = this.cartaoRepository.save(new Cartao(null,"1828393849","ciclaninho", LocalDateTime.now()));
        Assertions.assertNotNull(cartao);

        Mockito.when(this.accountClient.notificarBanco(cartao.getId())).thenReturn(ResponseEntity.ok(new NotificacaoViagemResponse("NAO_CRIADO")));

        var result = this.mvcRest.postEndpointWithHeaders(this.mockMvc,
                "/card/"+cartao.getId()+"/notificacao",
                asJsonString(new NotificacaoViagemRequest("Dinamarca", LocalDate.of(2000,10,10))));
        Assertions.assertEquals(400,result.getResponse().getStatus());
    }

}

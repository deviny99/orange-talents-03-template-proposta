package com.zup.academy.proposta.controller;

import com.zup.academy.global.controller.MvcRest;
import com.zup.academy.proposta.PropostaDataBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static com.zup.academy.global.utils.JsonMapper.asJsonString;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@DisplayName("Testa o Endpoint de Propostas")
class PropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MvcRest propostaTemplate;

    private String TARGET_ENDPOINT = "/";

    @Test
    @DisplayName("Deve cadastrar proposta com sucesso")
    @WithMockUser
    @Transactional
    @Rollback
    void cadastrarProposta() throws Exception {

        var mvcResult = this.propostaTemplate.postEndpoint(
                this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCpfValido()));
        Assertions.assertEquals(201,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse());
        Assertions.assertNotNull(mvcResult.getResponse().getHeader("Location"));
        Assertions.assertTrue(mvcResult.getResponse().containsHeader("Location"));
    }


    @Test
    @WithMockUser
    @DisplayName("Não deve cadastrar proposta com documento invalido")
    @Transactional
    @Rollback
    void naoDeveCadastrarComDocumentoInvalido() throws Exception {

        var mvcResult = this.propostaTemplate.postEndpoint(this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCpfInvalido()));

        Assertions.assertEquals(400,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser
    @DisplayName("Deve cadastrar proposta com através de um CPF válido")
    @Transactional
    @Rollback
    void deveCadastrarComCpfValido() throws Exception {

        var mvcResult = this.propostaTemplate.postEndpoint(
                this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCpfValido()));
        Assertions.assertEquals(201,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
        Assertions.assertNotNull(mvcResult.getResponse().getHeader("Location"));
        Assertions.assertTrue(mvcResult.getResponse().containsHeader("Location"));
    }

    @Test
    @WithMockUser
    @DisplayName("Deve cadastrar proposta com através de um CNPJ válido")
    @Transactional
    @Rollback
    void deveCadastrarComCnpjValido() throws Exception {
        var mvcResult = this.propostaTemplate.postEndpoint(this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCpfValido()));
        Assertions.assertEquals(201,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
        Assertions.assertNotNull(mvcResult.getResponse().getHeader("Location"));
        Assertions.assertTrue(mvcResult.getResponse().containsHeader("Location"));
    }

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Não deve cadastrar proposta repetida para o mesmo solicitante")
    void naoDeveCadastrarPropostaJaSolicitada() throws Exception {

        this.propostaTemplate.postEndpoint(
                this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCpfValido()));

        var mvcResult =  this.propostaTemplate.postEndpoint(
                this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCpfValido()));

        Assertions.assertEquals(422,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
    }




}

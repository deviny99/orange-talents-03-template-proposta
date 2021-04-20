package com.zup.academy.proposta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static com.zup.academy.proposta.JsonMapper.asJsonString;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@DisplayName("Testa o Endpoint de Propostas")
public class PropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String TARGET_ENDPOINT = "/";

    @Test
    @DisplayName("Deve cadastrar proposta com sucesso")
    @WithMockUser
    @Transactional
    @Rollback
    public void cadastrarProposta() throws Exception {

        var mvcResult = cadastrarPropostaComSucesso();
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
    public void naoDeveCadastrarComDocumentoInvalido() throws Exception {
        var mvcResult =  this.mockMvc.perform(post(TARGET_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(asJsonString(PropostaDataBuilder.propostaRequestCpfInvalido())))
                .andReturn();

        Assertions.assertEquals(400,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser
    @DisplayName("Deve cadastrar proposta com através de um CPF válido")
    @Transactional
    @Rollback
    public void deveCadastrarComCpfValido() throws Exception {

        var mvcResult = cadastrarPropostaComSucesso();
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
    public void deveCadastrarComCnpjValido() throws Exception {
        var mvcResult =  this.mockMvc.perform(post(TARGET_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(asJsonString(PropostaDataBuilder.propostaRequestCnpjValido())))
                .andReturn();

        Assertions.assertEquals(201,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
        Assertions.assertNotNull(mvcResult.getResponse().getHeader("Location"));
        Assertions.assertTrue(mvcResult.getResponse().containsHeader("Location"));
    }

    @Test
    @WithMockUser
    @DisplayName("Não deve cadastrar proposta repetida para o mesmo solicitante")
    @Transactional
    @Rollback
    public void naoDeveCadastrarPropostaJaSolicitada() throws Exception {

        cadastrarPropostaComSucesso();
        var mvcResult2 =  cadastrarPropostaComSucesso();

        Assertions.assertEquals(422,mvcResult2.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult2.getResponse().getContentAsString());
    }

    private MvcResult cadastrarPropostaComSucesso() throws Exception {
        return  this.mockMvc.perform(post(TARGET_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(asJsonString(PropostaDataBuilder.propostaRequestCpfValido())))
                .andReturn();
    }
}

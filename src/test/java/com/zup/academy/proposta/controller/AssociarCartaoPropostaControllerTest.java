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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static com.zup.academy.global.utils.JsonMapper.asJsonString;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@DisplayName("Testa o Endpoint de associação de cartão com proposta")
public class AssociarCartaoPropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MvcRest propostaTemplate;
    private String TARGET_ENDPOINT = "/";
    private String TARGET_ACCOUNT_ENDPOINT = "/accounts";

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Deve associar um numero de cartão com a proposta")
    void deveAssociarCartaoComProposta() throws Exception {

        MvcResult result = this.propostaTemplate.postEndpoint(
                this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCpfValido()));

        Assertions.assertEquals(201,result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse());

        String url = TARGET_ACCOUNT_ENDPOINT+"/"
                +this.formatarResponse(result.getResponse().getContentAsString());

        result = this.propostaTemplate.postEndpoint(
                this.mockMvc,
                url, "");

        Assertions.assertEquals(200,result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar que não existe proposta com o ID informado")
    void naoContemPropostaComIdInformado() throws Exception {

        String url = TARGET_ACCOUNT_ENDPOINT+"/1";
        MvcResult result = this.propostaTemplate.postEndpoint(
                this.mockMvc,
                url, "");

        Assertions.assertNotEquals(200,result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getContentAsString());

    }

    private String formatarResponse(String value){
        return value.replaceAll("\"","");
    }
}

package com.zup.academy.proposta.controller;

import com.zup.academy.global.controller.MvcRest;
import com.zup.academy.global.utils.JsonMapper;
import com.zup.academy.proposta.PropostaDataBuilder;
import com.zup.academy.proposta.domain.StatusProposta;
import com.zup.academy.proposta.dto.PropostaDetalhes;
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
@DisplayName("Testa os Endpoints de Proposta Controller")
class PropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MvcRest propostaTemplate;
    private String TARGET_ENDPOINT = "/";

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
    @DisplayName("Deve cadastrar proposta através de um CPF válido")
    @Transactional
    @Rollback
    void deveCadastrarComCpfValido() throws Exception {

        var mvcResult = this.cadastrarPropostaComSucesso();
        Assertions.assertEquals(201,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
        Assertions.assertNotNull(mvcResult.getResponse().getHeader("Location"));
        Assertions.assertTrue(mvcResult.getResponse().containsHeader("Location"));
    }

    @Test
    @WithMockUser
    @DisplayName("Deve cadastrar proposta através de um CNPJ válido")
    @Transactional
    @Rollback
    void deveCadastrarComCnpjValido() throws Exception {
        var mvcResult = this.propostaTemplate.postEndpoint(this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCnpjValido()));
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

        var mvcResult= this.cadastrarPropostaComSucesso();

        Assertions.assertEquals(201,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());

        mvcResult = this.cadastrarPropostaComSucesso();

        Assertions.assertEquals(422,mvcResult.getResponse().getStatus());
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Deve retornar detalhes da proposta sem estar com cartão associado")
    void detalhesProposta() throws Exception {

        var mvcResult = this.cadastrarPropostaComSucesso();
        String url = TARGET_ENDPOINT+formatarResponse(mvcResult.getResponse().getContentAsString());

        var resultDetalhesProposta = this.detalhesDaProposta(url);
        PropostaDetalhes propostaDetalhes = (PropostaDetalhes) JsonMapper.asJsonObject(resultDetalhesProposta.getResponse().getContentAsString(),PropostaDetalhes.class);
        Assertions.assertEquals(200,resultDetalhesProposta.getResponse().getStatus());
        Assertions.assertNotEquals(StatusProposta.ASSOCIADO.toString(),propostaDetalhes.getStatus());
    }


    @Test
    @WithMockUser
    @DisplayName("Não deve retornar detalhes de proposta inexistente")
    void detalhesPropostaInexistente() throws Exception {
        var resultDetalhesProposta = this.detalhesDaProposta(TARGET_ENDPOINT+formatarResponse("sadkldajfk234uijzkljsd0au3"));
        Assertions.assertNotEquals(200,resultDetalhesProposta.getResponse().getStatus());
    }


    private MvcResult detalhesDaProposta(String url) throws Exception {
        return this.propostaTemplate.getEndpoint(this.mockMvc,url,"");
    }

    private MvcResult cadastrarPropostaComSucesso() throws Exception {
        return  this.propostaTemplate.postEndpoint(
                this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCpfValido()));
    }

    private MvcResult associarPropostaComCartao(String id) throws Exception {
        String url2 = "/accounts/"
                +this.formatarResponse(id);
        return this.propostaTemplate.postEndpoint(
                this.mockMvc,
                url2, "");
    }

    private String formatarResponse(String value){
        return value.replaceAll("\"","");
    }

}

package com.zup.academy.proposta.scheduler;

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
public class AssociarCartaoPropostaTest {

    @Autowired
    private AssociarCartaoPropostaScheduler associarCartaoPropostaController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MvcRest propostaTemplate;

    private String TARGET_ENDPOINT = "/propostas";

    @Test
    @WithMockUser
    @Transactional
    @Rollback
    @DisplayName("Deve associar um numero de cartão com a proposta")
    void deveAssociarCartaoComProposta(){

      try {
          cadastrarPropostaComSucesso();
          this.associarCartaoPropostaController.associarCartaoComProposta();
      }catch (Exception e){
          Assertions.fail();
      }
    }

    private MvcResult cadastrarPropostaComSucesso() throws Exception {
        return  this.propostaTemplate.postEndpoint(
                this.mockMvc,
                TARGET_ENDPOINT,
                asJsonString(PropostaDataBuilder.propostaRequestCpfValido()));
    }

}

package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.dto.biometria.BiometriaRequest;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.global.controller.MvcRest;
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
import java.time.LocalDateTime;
import static com.zup.academy.global.utils.JsonMapper.asJsonString;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@DisplayName("Testa Endpoints de vinculo de biometria")
public class VinculoBiometriaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MvcRest mvcRest;
    private final String CARD_ENDPOINT = "/cards";
    @Autowired
    private CartaoRepository cartaoRepository;

    @Test
    @Transactional
    @Rollback
    @WithMockUser
    @DisplayName("Deve cadastrar biometria")
    void deveCadastrarBiometria() throws Exception {

        Cartao cartao = this.cartaoRepository.save(new Cartao(null,"1828393849","ciclaninho", LocalDateTime.now()));
        var result = this.mvcRest.postEndpoint(this.mockMvc,
                CARD_ENDPOINT+"/"+cartao.getId()+"/biometria",
                asJsonString(new BiometriaRequest("adfasfasfaj")));
        Assertions.assertEquals(201,result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getHeader("Location"));
        Assertions.assertTrue(result.getResponse().containsHeader("Location"));
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser
    @DisplayName("Não deve cadastrar biometria com cartão não existente")
    void naoDeveCadastrarBiometriaParaCartaoInexistente() throws Exception {
        var result = this.mvcRest.postEndpoint(this.mockMvc,
                CARD_ENDPOINT+"/"+0+"/biometria",
                asJsonString(new BiometriaRequest("sajadsfjasd8fu23u4j234u")));
        Assertions.assertEquals(404,result.getResponse().getStatus());
    }

}

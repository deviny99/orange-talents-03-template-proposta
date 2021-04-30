package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.dto.carteira.CarteiraDigitalRequest;
import com.zup.academy.cartao.dto.carteira.CarteiraDigitalResponseFeign;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.global.controller.MvcRest;
import com.zup.academy.proxies.contas.ContasProxy;
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
import static com.zup.academy.global.utils.JsonMapper.asJsonString;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@DisplayName("Testa endpoint de associação de cartão com carteira digital")
public class CarteiraDigitalControllerTest {

    @Autowired
    private MvcRest mvcRest;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CartaoRepository cartaoRepository;
    @MockBean
    private ContasProxy contasProxy;

    @Test
    @Rollback
    @WithMockUser
    @Transactional
    @DisplayName("Deve associar cartão com carteira digital do Paypal")
    void deveAssociarCartaoComCarteiraDigitalPaypal() throws Exception {
        Cartao cartao = this.cartaoRepository.save(new Cartao(null,"1828393849","ciclaninho", LocalDateTime.now()));
        Assertions.assertNotNull(cartao);

       // CarteiraDigitalDtoFeign carteiraDigitalDto =  new CarteiraDigitalDtoFeign("email@email.com", CarteiraDigital.PAYPAL);

        Mockito.when(this.contasProxy.associarCarteiraDigital(
                Mockito.any(),Mockito.any()))
                .thenReturn(new CarteiraDigitalResponseFeign("ASSOCIADA","2"));

        var result = this.mvcRest.postEndpoint(this.mockMvc,
                "/cards/"+cartao.getId()+"/paypal",
                asJsonString(new CarteiraDigitalRequest("email@email.com")));
        Assertions.assertEquals(201,result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getHeader("Location"));
        Assertions.assertTrue(result.getResponse().containsHeader("Location"));
    }

    @Test
    @Rollback
    @WithMockUser
    @Transactional
    @DisplayName("Não deve associar o mesmo cartão com carteira digital do Paypal")
    void naoDeveAssociarOmesmoCartaoParaCarteiraPaypal() throws Exception {

        Cartao cartao = this.cartaoRepository.save(new Cartao(null,"1828393849","ciclaninho", LocalDateTime.now()));

        Mockito.when(this.contasProxy.associarCarteiraDigital(
                Mockito.any(),Mockito.any()))
                .thenReturn(new CarteiraDigitalResponseFeign("ASSOCIADA","2"));

        var result = this.mvcRest.postEndpoint(this.mockMvc,
                "/cards/"+cartao.getId()+"/paypal",
                asJsonString(new CarteiraDigitalRequest("email@email.com")));
        Assertions.assertEquals(201,result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getHeader("Location"));
        Assertions.assertTrue(result.getResponse().containsHeader("Location"));
        var result2 = this.mvcRest.postEndpoint(this.mockMvc,
                "/cards/"+cartao.getId()+"/paypal",
                asJsonString(new CarteiraDigitalRequest("email@email.com")));
        Assertions.assertEquals(422,result2.getResponse().getStatus());

    }

    @Test
    @Rollback
    @WithMockUser
    @Transactional
    @DisplayName("Deve associar cartão com carteira digital do Samsung Pay")
    void deveAssociarCartaoComCarteiraDigitalSamsungPay() throws Exception {
        Cartao cartao = this.cartaoRepository.save(new Cartao(null,"1828393849","ciclaninho", LocalDateTime.now()));
        Assertions.assertNotNull(cartao);

       // CarteiraDigitalDtoFeign carteiraDigitalDto =  new CarteiraDigitalDtoFeign("email@email.com", CarteiraDigital.PAYPAL);

        Mockito.when(this.contasProxy.associarCarteiraDigital(
                Mockito.any(),Mockito.any()))
                .thenReturn(new CarteiraDigitalResponseFeign("ASSOCIADA","2"));

        var result = this.mvcRest.postEndpoint(this.mockMvc,
                "/cards/"+cartao.getId()+"/samsung",
                asJsonString(new CarteiraDigitalRequest("email@email.com")));
        Assertions.assertEquals(201,result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getHeader("Location"));
        Assertions.assertTrue(result.getResponse().containsHeader("Location"));
    }

    @Test
    @Rollback
    @WithMockUser
    @Transactional
    @DisplayName("Não deve associar o mesmo cartão com carteira digital do Samsung Pay")
    void naoDeveAssociarOmesmoCartaoParaCarteiraSamsungPay() throws Exception {

        Cartao cartao = this.cartaoRepository.save(new Cartao(null,"1828393849","ciclaninho", LocalDateTime.now()));

        Mockito.when(this.contasProxy.associarCarteiraDigital(
                Mockito.any(),Mockito.any()))
                .thenReturn(new CarteiraDigitalResponseFeign("ASSOCIADA","2"));

        var result = this.mvcRest.postEndpoint(this.mockMvc,
                "/cards/"+cartao.getId()+"/samsung",
                asJsonString(new CarteiraDigitalRequest("email@email.com")));
        Assertions.assertEquals(201,result.getResponse().getStatus());
        Assertions.assertNotNull(result.getResponse().getHeader("Location"));
        Assertions.assertTrue(result.getResponse().containsHeader("Location"));
        var result2 = this.mvcRest.postEndpoint(this.mockMvc,
                "/cards/"+cartao.getId()+"/samsung",
                asJsonString(new CarteiraDigitalRequest("email@email.com")));
        Assertions.assertEquals(422,result2.getResponse().getStatus());

    }
}

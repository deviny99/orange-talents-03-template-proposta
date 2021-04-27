package com.zup.academy.global.healthcheck;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.metrics.AutoConfigureMetrics;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMetrics
@DisplayName("Testa os endpoints de HelthCheck da aplicação")
public class HealthCheckTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Verifica endpoint de metricas com prometheus")
    @WithMockUser(roles = "ADMIN_METRICS")
    void verificaEndpointDaSaudeDaAplicacaoPrometheus() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/actuator/prometheus")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(200,result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Verifica endpoint da saúde da aplicacao")
    @WithMockUser(roles = "ADMIN_METRICS")
    void verificaEndpointDaSaudeDaAplicacao() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/actuator/health")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assertions.assertEquals(200,result.getResponse().getStatus());

    }

    @Test
    @DisplayName("Não deve autorizar os endpoints das metricas para outros niveis de acesso")
    @WithMockUser(roles = "USER")
    void naoDeveAutorizarEndpointDeMetricas() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/actuator/health")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assertions.assertEquals(403,result.getResponse().getStatus());

    }

}
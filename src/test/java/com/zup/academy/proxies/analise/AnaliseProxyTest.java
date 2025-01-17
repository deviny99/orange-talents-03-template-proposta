package com.zup.academy.proxies.analise;

import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proxies.analise.dto.SolicitacaoAnaliseRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@DisplayName("Teste de consulta de dados do solicitante")
public class AnaliseProxyTest {

    @MockBean
    private AnaliseProxy analiseProxy;
    private SolicitacaoAnaliseRequest solicitacaoAnaliseRequest;

    @BeforeEach
    void init() {
        this.solicitacaoAnaliseRequest  = new SolicitacaoAnaliseRequest("506.070.720-24","Teste da Silva","1");
    }

    @Test
    @DisplayName("Não deve retornar solicitação com restrição")
    void deveRetornarSolicitacaoSemRestricao(){

        ResponseEntity responseEntity =  ResponseEntity.created(null).body("id uuid");
        Mockito.when(this.analiseProxy.consultarClient(this.solicitacaoAnaliseRequest )).thenReturn(responseEntity);

        ResponseEntity response = this.analiseProxy.consultarClient(this.solicitacaoAnaliseRequest );
        Assertions.assertEquals(201,response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Deve retornar solicitação com restrição")
    void deveRetornarSolicitacaoComRestricao(){
        try{
            Mockito.when(this.analiseProxy.consultarClient(this.solicitacaoAnaliseRequest )).thenThrow(CustomException.unprocessable(""));
            this.analiseProxy.consultarClient(this.solicitacaoAnaliseRequest);
            Assertions.fail();
        }catch (CustomException customException)
        {
            Assertions.assertEquals(422,customException.getStatus().value());
            Assertions.assertNotNull(customException.getLocalizedMessage());
        }
    }
}

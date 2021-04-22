package com.zup.academy.web.clients.solicitacao;

import com.zup.academy.global.exception.CustomException;
import com.zup.academy.web.clients.solicitacao.dto.SolicitacaoAnaliseRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@DisplayName("Teste de consulta de dados do solicitante")
public class SolicitacaoClientTest {

    @Autowired
    private SolicitacaoClient solicitacaoClient;

    @Test
    @DisplayName("Não deve retornar solicitação com restrição")
    void deveRetornarSolicitacaoSemRestricao(){
        ResponseEntity response = solicitacaoClient.consultarClient(new SolicitacaoAnaliseRequest("506.070.720-24","Teste da Silva","1"));
        Assertions.assertEquals(201,response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Deve retornar solicitação com restrição")
    void deveRetornarSolicitacaoComRestricao(){
        try{
            ResponseEntity response = solicitacaoClient.consultarClient(new SolicitacaoAnaliseRequest("306.070.720-24","Teste da Silva","1"));
            Assertions.fail();
        }catch (CustomException customException)
        {
            Assertions.assertEquals(422,customException.getStatus().value());
            Assertions.assertNotNull(customException.getLocalizedMessage());
        }
    }

}

package com.zup.academy.proxies.solicitacao;

import com.zup.academy.proxies.solicitacao.dto.SolicitacaoAnaliseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "solicitacaoAnalise", url = "${clients.solicitacao.url}")
public interface SolicitacaoClient {

    @RequestMapping(value = "/solicitacao",method = RequestMethod.POST)
    ResponseEntity<?> consultarClient(@RequestBody SolicitacaoAnaliseRequest solicitacaoAnaliseRequest);

}

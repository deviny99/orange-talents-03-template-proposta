package com.zup.academy.proxies.analise;

import com.zup.academy.proxies.analise.dto.SolicitacaoAnaliseRequest;
import com.zup.academy.proxies.analise.dto.SolicitacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "solicitacaoAnalise", url = "${clients.analise.url}")
public interface AnaliseProxy {

    @RequestMapping(value = "/solicitacao",method = RequestMethod.POST)
    ResponseEntity<SolicitacaoResponse> consultarClient(@RequestBody SolicitacaoAnaliseRequest solicitacaoAnaliseRequest);

}

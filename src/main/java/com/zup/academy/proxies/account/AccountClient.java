package com.zup.academy.proxies.account;

import com.zup.academy.cartao.dto.CartaoResponse;
import com.zup.academy.proposta.dto.PropostaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "accountService", url = "${clients.account.url}")
public interface AccountClient {

    @PostMapping
    ResponseEntity<CartaoResponse> addCartao(@RequestBody PropostaDto propostaDto);

    @PostMapping("/{id}/bloqueios")
    ResponseEntity<String> bloquearCartao(@PathVariable("id") Long id);
}

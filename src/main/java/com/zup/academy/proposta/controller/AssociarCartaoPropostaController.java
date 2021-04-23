package com.zup.academy.proposta.controller;

import com.zup.academy.cartao.dto.CartaoResponse;
import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proposta.domain.Proposta;
import com.zup.academy.proposta.dto.PropostaDto;
import com.zup.academy.proposta.repository.PropostaRespository;
import com.zup.academy.proxies.account.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
class AssociarCartaoPropostaController {

    private final PropostaRespository propostaRespository;
    private final AccountClient accountClient;

    @Autowired
    public AssociarCartaoPropostaController(PropostaRespository propostaRespository,AccountClient accountClient){
        this.propostaRespository = propostaRespository;
        this.accountClient = accountClient;
    }

    @PostMapping(value = "/{id}")
    ResponseEntity<?> associarCartaoComProposta(@PathVariable("id") String id){

        Proposta proposta = this.propostaRespository.findById(UUID.fromString(id)).orElseThrow(() ->{
            throw CustomException.notFound("Não contem proposta com o ID informado");
        });

        ResponseEntity<CartaoResponse> cartaoResponse = this.accountClient.addCartao(new PropostaDto(proposta));
        proposta.vincularCartao(cartaoResponse.getBody().toModel(null));
        this.propostaRespository.save(proposta);

        return ResponseEntity.ok().build();
    }

}

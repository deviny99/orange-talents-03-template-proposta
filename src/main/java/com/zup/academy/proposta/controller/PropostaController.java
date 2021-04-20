package com.zup.academy.proposta.controller;

import com.zup.academy.proposta.domain.Proposta;
import com.zup.academy.proposta.dto.PropostaRequest;
import com.zup.academy.proposta.repository.PropostaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping
class PropostaController {

    private final PropostaRespository propostaRespository;

    @Autowired
    public PropostaController(PropostaRespository propostaRespository){
        this.propostaRespository = propostaRespository;
    }

    @Transactional
    @PostMapping
    ResponseEntity<?> cadastrarProposta(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uriCB){

        Proposta proposta = propostaRequest.toModel(null);
        proposta = this.propostaRespository.save(proposta);

        return ResponseEntity.created(
                uriCB.path("/{id}")
                        .buildAndExpand(proposta.getId())
                        .toUri())
                .body(proposta.getId());
    }

}

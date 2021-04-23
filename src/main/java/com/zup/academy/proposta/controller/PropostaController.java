package com.zup.academy.proposta.controller;

import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proposta.domain.Proposta;
import com.zup.academy.proposta.dto.PropostaDetalhes;
import com.zup.academy.proposta.dto.PropostaRequest;
import com.zup.academy.proposta.repository.PropostaRespository;
import com.zup.academy.proposta.validation.VerificaPropostaJaSolicitada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping
class PropostaController {

    private final PropostaRespository propostaRespository;
    private final VerificaPropostaJaSolicitada propostaJaSolicitada;

    @Autowired
    public PropostaController(PropostaRespository propostaRespository, VerificaPropostaJaSolicitada propostaJaSolicitada){
        this.propostaRespository = propostaRespository;
        this.propostaJaSolicitada = propostaJaSolicitada;
    }

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(this.propostaJaSolicitada);
    }


    @PostMapping
    @Transactional
    ResponseEntity<UUID> cadastrarProposta(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uriCB){

        Proposta proposta = propostaRequest.toModel(null);
        proposta = this.propostaRespository.save(proposta);

        return ResponseEntity.created(
                uriCB.path("/{id}")
                        .buildAndExpand(proposta.getId())
                        .toUri())
                .body(proposta.getId());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    ResponseEntity<PropostaDetalhes>detalhesProposta(@PathVariable("id") String id){

        Proposta proposta = this.propostaRespository.findById(UUID.fromString(id)).orElseThrow(()->{
            throw CustomException.notFound("Não existe proposta com ID informado");
        });

        return ResponseEntity.ok(new PropostaDetalhes(proposta));
    }


}
package com.zup.academy.proposta.controller;

import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proposta.domain.Proposta;
import com.zup.academy.proposta.dto.PropostaDetalhes;
import com.zup.academy.proposta.dto.PropostaRequest;
import com.zup.academy.proposta.repository.PropostaRespository;
import com.zup.academy.proposta.validation.VerificaPropostaJaSolicitada;
import com.zup.academy.proxies.analise.AnaliseProxy;
import com.zup.academy.proxies.analise.dto.SolicitacaoAnaliseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/propostas")
class PropostaController {

    private final PropostaRespository propostaRespository;
    private final VerificaPropostaJaSolicitada propostaJaSolicitada;
    private final AnaliseProxy analiseProxy;

    @Autowired
    public PropostaController(PropostaRespository propostaRespository, VerificaPropostaJaSolicitada propostaJaSolicitada, AnaliseProxy analiseProxy){
        this.propostaRespository = propostaRespository;
        this.propostaJaSolicitada = propostaJaSolicitada;
        this.analiseProxy = analiseProxy;
    }

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(this.propostaJaSolicitada);
    }


    @PostMapping
    @Transactional
    ResponseEntity<String> cadastrarProposta(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uriCB){

        Proposta proposta = propostaRequest.toModel(null);
        proposta = this.propostaRespository.save(proposta);

        var respostaLegadoSolicitacao = this.analiseProxy.consultarClient(new SolicitacaoAnaliseRequest(proposta.getDocumento(),proposta.getNome(),proposta.getId().toString()));
        proposta = respostaLegadoSolicitacao.getBody().verificaStatusSolicitacao(proposta);
        proposta = this.propostaRespository.save(proposta);

        return ResponseEntity.created(
                uriCB.path("/{id}")
                        .buildAndExpand(proposta.getId())
                        .toUri())
                .body(proposta.getId().toString());
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
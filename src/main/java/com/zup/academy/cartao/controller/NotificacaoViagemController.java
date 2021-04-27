package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.domain.NotificacaoViagem;
import com.zup.academy.cartao.dto.NotificacaoViagemRequest;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.cartao.repository.NotificacaoViagemRepository;
import com.zup.academy.global.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/card")
class NotificacaoViagemController {

    private final CartaoRepository cartaoRepository;
    private final NotificacaoViagemRepository notificacaoViagemRepository;

    public NotificacaoViagemController(CartaoRepository cartaoRepository,NotificacaoViagemRepository notificacaoViagemRepository){
        this.notificacaoViagemRepository = notificacaoViagemRepository;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/{id}/notificacao")
    @Transactional
    ResponseEntity<?> notificarViagem(@PathVariable("id") Long idCartao,
                                      @RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("x-forwarded-for") String ipAddress,
                                      @RequestBody @Valid NotificacaoViagemRequest notificacaoRequest,
                                      UriComponentsBuilder uriCB){

        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(()->{
            throw CustomException.notFound("Não contem um cartão com o ID informado");
        });

        NotificacaoViagem notificacaoViagem = notificacaoRequest.toModel(null,cartao,userAgent,ipAddress);
        notificacaoViagem = this.notificacaoViagemRepository.save(notificacaoViagem);

        return ResponseEntity.created(uriCB.path("/card/notificacao/{id}").buildAndExpand(notificacaoViagem.getId())
                .toUri())
                .body(notificacaoViagem.getId());
    }
}

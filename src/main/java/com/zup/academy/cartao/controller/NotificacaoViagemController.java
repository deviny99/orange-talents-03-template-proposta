package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.domain.NotificacaoViagem;
import com.zup.academy.cartao.domain.StatusNotificacaoViagem;
import com.zup.academy.cartao.dto.viagem.NotificacaoViagemRequestFeign;
import com.zup.academy.cartao.dto.viagem.NotificacaoViagemResponseFeign;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.cartao.repository.NotificacaoViagemRepository;
import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proxies.contas.ContasProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;

@RestController
@RequestMapping("/cards")
class NotificacaoViagemController {

    private final CartaoRepository cartaoRepository;
    private final NotificacaoViagemRepository notificacaoViagemRepository;
    private final ContasProxy contasProxy;

    public NotificacaoViagemController(CartaoRepository cartaoRepository,NotificacaoViagemRepository notificacaoViagemRepository,
                                       ContasProxy contasProxy){
        this.notificacaoViagemRepository = notificacaoViagemRepository;
        this.cartaoRepository = cartaoRepository;
        this.contasProxy = contasProxy;
    }

    @PostMapping("/{id}/notificacao")
    @Transactional
    ResponseEntity<?> notificarViagem(@PathVariable("id") Long idCartao,
                                      @RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("x-forwarded-for") String ipAddress,
                                      @RequestBody @Valid NotificacaoViagemRequestFeign notificacaoRequest,
                                      UriComponentsBuilder uriCB){

        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(()->{
            throw CustomException.notFound("Não contem um cartão com o ID informado");
        });


        NotificacaoViagem notificacaoViagem = notificacaoRequest.toModel(null,cartao,userAgent,ipAddress);
        notificacaoViagem = this.notificacaoViagemRepository.save(notificacaoViagem);

        this.notificarBanco(cartao.getNumero(),notificacaoRequest);

        return ResponseEntity.created(uriCB.path("/cards/notificacao/{id}").buildAndExpand(notificacaoViagem.getId())
                .toUri())
                .body(notificacaoViagem.getId());
    }

    private void notificarBanco(String numeroCartao, NotificacaoViagemRequestFeign notificacaoViagemRequest){
        NotificacaoViagemResponseFeign notificacaoViagemResponse = this.contasProxy.notificarBanco(numeroCartao,notificacaoViagemRequest);

        if (!notificacaoViagemResponse.toResponse().equals(StatusNotificacaoViagem.CRIADO)){
            throw CustomException.unprocessable("Não foi possivel criar a notificação de viagem no banco");
        }
    }
}

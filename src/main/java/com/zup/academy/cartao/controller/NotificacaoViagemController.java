package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.domain.NotificacaoViagem;
import com.zup.academy.cartao.domain.StatusNotificacaoViagem;
import com.zup.academy.cartao.dto.NotificacaoViagemRequest;
import com.zup.academy.cartao.dto.NotificacaoViagemResponse;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.cartao.repository.NotificacaoViagemRepository;
import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proxies.account.AccountClient;
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
    private final AccountClient accountClient;

    public NotificacaoViagemController(CartaoRepository cartaoRepository,NotificacaoViagemRepository notificacaoViagemRepository,
                                       AccountClient accountClient){
        this.notificacaoViagemRepository = notificacaoViagemRepository;
        this.cartaoRepository = cartaoRepository;
        this.accountClient = accountClient;
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

        this.notificarBanco(idCartao);

        NotificacaoViagem notificacaoViagem = notificacaoRequest.toModel(null,cartao,userAgent,ipAddress);
        notificacaoViagem = this.notificacaoViagemRepository.save(notificacaoViagem);

        return ResponseEntity.created(uriCB.path("/card/notificacao/{id}").buildAndExpand(notificacaoViagem.getId())
                .toUri())
                .body(notificacaoViagem.getId());
    }

    private void notificarBanco(Long idCartao){
        ResponseEntity<NotificacaoViagemResponse> notificacaoViagemResponse = this.accountClient.notificarBanco(idCartao);
        if (notificacaoViagemResponse.getStatusCodeValue() != 200){
            throw CustomException.badRequest(notificacaoViagemResponse.getStatusCode().toString());
        }

        if (!notificacaoViagemResponse.getBody().toResponse().equals(StatusNotificacaoViagem.CRIADO)){
            throw CustomException.unprocessable("Não foi possivel criar a notificação de viagem no banco");
        }
    }
}

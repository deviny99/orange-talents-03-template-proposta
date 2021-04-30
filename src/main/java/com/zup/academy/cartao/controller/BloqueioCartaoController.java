package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.BloqueioCartao;
import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.domain.StatusBloqueioCartao;
import com.zup.academy.cartao.dto.bloqueio.BloqueioCartaoRequestFeign;
import com.zup.academy.cartao.dto.bloqueio.BloqueioCartaoResponseFeign;
import com.zup.academy.cartao.repository.BloqueioRepository;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proxies.contas.ContasProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
class BloqueioCartaoController {

    private final CartaoRepository cartaoRepository;
    private final BloqueioRepository bloqueioRepository;
    private final ContasProxy contasProxy;

    @Value("${spring.application.name}")
    private String sistemaName;

    @Autowired
    public BloqueioCartaoController(CartaoRepository cartaoRepository,
                                    BloqueioRepository bloqueioRepository,
                                    ContasProxy contasProxy){

        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.contasProxy = contasProxy;
    }

    @PostMapping("/bloqueio/{id}")
    ResponseEntity<String> bloquearCartao(@PathVariable("id") Long id,
                                     @RequestHeader("User-Agent") String userAgent,
                                     @RequestHeader("x-forwarded-for") String ipAddress){

        Cartao cartao = this.cartaoRepository.findById(id).orElseThrow(()->{
            throw CustomException.notFound("Não contém cartão cadastrado com o ID informado");
        });

        if(this.bloqueioRepository.existsByCartao(cartao)){
            throw CustomException.unprocessable("Esse cartão já está bloqueado");
        }

        BloqueioCartao bloqueio = new BloqueioCartao(null,ipAddress,userAgent);
        bloqueio.bloquearCartao(cartao);
        bloqueio = this.bloqueioRepository.save(bloqueio);

        this.notificarLegadoAccount(cartao.getNumero());

        return ResponseEntity.ok(bloqueio.getId().toString());
    }

    private void notificarLegadoAccount(String numeroCartao){

        BloqueioCartaoResponseFeign responseCartaoLegado = this.contasProxy.bloquearCartao(numeroCartao, new BloqueioCartaoRequestFeign(this.sistemaName));

        if (!responseCartaoLegado.toResponse().equals(StatusBloqueioCartao.BLOQUEADO)){
            throw CustomException.unprocessable("Cartão não bloqueado");
        }
    }

}
package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.BloqueioCartao;
import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.domain.StatusBloqueioCartao;
import com.zup.academy.cartao.repository.BloqueioRepository;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proxies.account.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
class BloqueioCartaoController {

    private final CartaoRepository cartaoRepository;
    private final BloqueioRepository bloqueioRepository;
    private final AccountClient accountClient;

    @Autowired
    public BloqueioCartaoController(CartaoRepository cartaoRepository,
                                    BloqueioRepository bloqueioRepository,
                                    AccountClient accountClient){

        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.accountClient = accountClient;
    }

    @PostMapping("/bloqueio/{id}")
    ResponseEntity<?> bloquearCartao(@PathVariable("id") Long id,
                                     @RequestHeader("User-Agent") String userAgent,
                                     @RequestHeader("x-forwarded-for") String ipAddress){

        Cartao cartao = this.cartaoRepository.findById(id).orElseThrow(()->{
            throw CustomException.notFound("Não contém cartão cadastrado com o ID informado");
        });

        this.notificarLegadoAccount(cartao.getId());

        if(this.bloqueioRepository.findByCartao(cartao)){
            throw CustomException.unprocessable("Esse cartão já está bloqueado");
        }

        BloqueioCartao bloqueio = new BloqueioCartao(null,ipAddress,userAgent);
        bloqueio.bloquearCartao(cartao);
        cartao.bloquear();
        this.bloqueioRepository.save(bloqueio);

        return ResponseEntity.ok(bloqueio.getId());
    }

    private void notificarLegadoAccount(Long cartaoId){

        ResponseEntity<String> responseCartaoLegado = this.accountClient.bloquearCartao(cartaoId);
        if (responseCartaoLegado.getStatusCode().value() != 200){
            throw CustomException.badRequest(responseCartaoLegado.getStatusCode().toString());
        }

        if (!responseCartaoLegado.getBody().equals(StatusBloqueioCartao.BLOQUEADO.toString())){
            throw CustomException.unprocessable("Cartão não bloqueado");
        }
    }

}

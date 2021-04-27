package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.BloqueioCartao;
import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.repository.BloqueioRepository;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.global.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/card")
public class BloqueioCartaoController {

    private final CartaoRepository cartaoRepository;
    private final BloqueioRepository bloqueioRepository;

    @Autowired
    public BloqueioCartaoController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository){
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
    }

    @PostMapping("/bloqueio/{id}")
    ResponseEntity<?> bloquearCartao(@PathVariable("id") Long id, HttpServletRequest request){
        String userAgente = request.getHeader("User-Agent");
        String ip = request.getHeader("x-forwarded-for");
        if ((userAgente == null || userAgente.isBlank()) &&
                (ip == null || ip.isBlank())){
            throw CustomException.badRequest("Headers User-Agent e X-Forwarded-For são obrigatórios " +
                    "para essa requisição");
        }

        Cartao cartao = this.cartaoRepository.findById(id).orElseThrow(()->{
            throw CustomException.notFound("Não contém cartão cadastrado com o ID informado");
        });

        if(this.bloqueioRepository.findByCartao(cartao)){
            throw CustomException.unprocessable("Esse cartão já está bloqueado");
        }

        BloqueioCartao bloqueio = new BloqueioCartao(null,ip,userAgente);
        bloqueio.bloquearCartao(cartao);
        this.bloqueioRepository.save(bloqueio);

        return ResponseEntity.ok(bloqueio.getId());
    }

}

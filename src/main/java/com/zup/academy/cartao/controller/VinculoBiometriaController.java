package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Biometria;
import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.dto.biometria.BiometriaRequest;
import com.zup.academy.cartao.repository.BiometriaRepository;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.global.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cards")
public class VinculoBiometriaController {

    private final CartaoRepository cartaoRepository;
    private final BiometriaRepository biometriaRepository;

    @Autowired
    public VinculoBiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository){
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @Transactional
    @PostMapping("/{id}/biometria")
    ResponseEntity<?> cadastrarBiometria(@PathVariable("id") Long id,@RequestBody @Valid BiometriaRequest biometriaRequest){

        Cartao cartao = this.cartaoRepository.findById(id).orElseThrow(()->{
            throw CustomException.notFound("Não contem um cartão com o ID informado");
        });

        Biometria biometria = this.biometriaRepository.save(biometriaRequest.toModel(null));
        cartao.vincularBiometria(biometria);
        this.cartaoRepository.save(cartao);
        return ResponseEntity.created(URI.create(String.format("/cards/biometria/%d",biometria.getId()))).build();
    }

}
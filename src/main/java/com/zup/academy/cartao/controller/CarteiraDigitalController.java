package com.zup.academy.cartao.controller;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.cartao.domain.CarteiraDigital;
import com.zup.academy.cartao.dto.carteira.CarteiraDigitalDtoFeign;
import com.zup.academy.cartao.dto.carteira.CarteiraDigitalRequest;
import com.zup.academy.cartao.repository.CartaoRepository;
import com.zup.academy.global.exception.CustomException;
import com.zup.academy.proxies.contas.ContasProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;

@RestController
@RequestMapping("/cards")
class CarteiraDigitalController {

    private final CartaoRepository cartaoRepository;
    private final ContasProxy contasProxy;

    @Autowired
    public CarteiraDigitalController(CartaoRepository cartaoRepository, ContasProxy contasProxy){
        this.cartaoRepository = cartaoRepository;
        this.contasProxy = contasProxy;
    }

    @Transactional
    @PostMapping("/{id}/paypal")
    ResponseEntity<?> associarCarteiraPaypal(@PathVariable("id") Long id, @Valid @RequestBody CarteiraDigitalRequest carteiraRequest,
                                             UriComponentsBuilder uri){

        String idCarteira = getAssociarCarteira(id,carteiraRequest,CarteiraDigital.PAYPAL);

        return ResponseEntity.created(uri.path("/cards/carteiras/{id}").buildAndExpand(idCarteira).toUri())
                .body(idCarteira);
    }

    @Transactional
    @PostMapping("/{id}/samsung")
    ResponseEntity<?> associarCarteiraSamsung(@PathVariable("id") Long id, @Valid @RequestBody CarteiraDigitalRequest carteiraRequest,
                                              UriComponentsBuilder uri){

        String idCarteira = getAssociarCarteira(id,carteiraRequest,CarteiraDigital.SAMSUNG_PAY);

        return ResponseEntity.created(uri.path("/cards/carteiras/{id}").buildAndExpand(idCarteira).toUri())
                .body(idCarteira);
    }

    private String getAssociarCarteira(Long id, CarteiraDigitalRequest carteiraDigitalRequest, CarteiraDigital carteiraDigital){
        Cartao cartao = this.cartaoRepository.findById(id).orElseThrow(()->{
            throw CustomException.notFound("Não contem um cartão com o ID informado");
        });

        cartao.associarCarteira(carteiraDigital);
        this.cartaoRepository.save(cartao);
        return this.associarCarteiraLegado(cartao.getNumero(),
                new CarteiraDigitalDtoFeign(carteiraDigitalRequest.getEmail(),carteiraDigital));
    }

    private String associarCarteiraLegado(String numeroCartao, CarteiraDigitalDtoFeign carteiraDigitalDto){

        var responseCartaoLegado =  this.contasProxy.associarCarteiraDigital(numeroCartao, carteiraDigitalDto);
       if (!responseCartaoLegado.getResultado().equals("ASSOCIADA")){
           throw CustomException.unprocessable("Cartão não foi associado com a carteira digital");
       }

        return responseCartaoLegado.getId();
    }

}
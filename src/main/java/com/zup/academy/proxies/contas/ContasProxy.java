package com.zup.academy.proxies.contas;

import com.zup.academy.cartao.dto.CartaoResponseFeign;
import com.zup.academy.cartao.dto.bloqueio.BloqueioCartaoRequestFeign;
import com.zup.academy.cartao.dto.bloqueio.BloqueioCartaoResponseFeign;
import com.zup.academy.cartao.dto.carteira.CarteiraDigitalDtoFeign;
import com.zup.academy.cartao.dto.carteira.CarteiraDigitalResponseFeign;
import com.zup.academy.cartao.dto.viagem.NotificacaoViagemRequestFeign;
import com.zup.academy.cartao.dto.viagem.NotificacaoViagemResponseFeign;
import com.zup.academy.proposta.dto.PropostaDtoFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "accountService", url = "${clients.contas.url}")
public interface ContasProxy {

    @PostMapping
    CartaoResponseFeign addCartao(@RequestBody PropostaDtoFeign propostaDto);

    @PostMapping("/{id}/bloqueios")
    BloqueioCartaoResponseFeign bloquearCartao(@PathVariable("id") String id, @RequestBody BloqueioCartaoRequestFeign bloqueioCartaoRequest);

    @PostMapping("/{id}/avisos")
    NotificacaoViagemResponseFeign notificarBanco(@PathVariable("id") String id,
                                                  @RequestBody NotificacaoViagemRequestFeign notificacaoViagemRequest);

    @PostMapping(value = "/{id}/carteiras",consumes = "application/json")
    CarteiraDigitalResponseFeign associarCarteiraDigital(@PathVariable("id") String id,
                                                         @RequestBody CarteiraDigitalDtoFeign carteiraDigitalDto);

}

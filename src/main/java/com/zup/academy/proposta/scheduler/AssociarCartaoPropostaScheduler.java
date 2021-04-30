package com.zup.academy.proposta.scheduler;

import com.zup.academy.cartao.dto.CartaoResponseFeign;
import com.zup.academy.proposta.domain.StatusProposta;
import com.zup.academy.proposta.dto.PropostaDtoFeign;
import com.zup.academy.proposta.repository.PropostaRespository;
import com.zup.academy.proxies.contas.ContasProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class AssociarCartaoPropostaScheduler {

    private final PropostaRespository propostaRespository;
    private final ContasProxy contasProxy;

    @Autowired
    public AssociarCartaoPropostaScheduler(PropostaRespository propostaRespository, ContasProxy contasProxy){
        this.propostaRespository = propostaRespository;
        this.contasProxy = contasProxy;
    }

    @Async
    @Scheduled(fixedDelay = 50000)
    @Transactional
    void associarCartaoComProposta(){

        this.propostaRespository.findByStatusProposta(StatusProposta.SEM_RESTRICAO).forEach(proposta ->{
            CartaoResponseFeign cartaoResponse = this.contasProxy.addCartao(new PropostaDtoFeign(proposta));
            proposta.vincularCartao(cartaoResponse.toModel(null));
            this.propostaRespository.save(proposta);

        });
    }

}

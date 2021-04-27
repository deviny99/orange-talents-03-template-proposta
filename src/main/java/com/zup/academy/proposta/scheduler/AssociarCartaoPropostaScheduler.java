package com.zup.academy.proposta.scheduler;

import com.zup.academy.cartao.dto.CartaoResponse;
import com.zup.academy.proposta.domain.StatusProposta;
import com.zup.academy.proposta.dto.PropostaDto;
import com.zup.academy.proposta.repository.PropostaRespository;
import com.zup.academy.proxies.account.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class AssociarCartaoPropostaScheduler {

    private final PropostaRespository propostaRespository;
    private final AccountClient accountClient;

    @Autowired
    public AssociarCartaoPropostaScheduler(PropostaRespository propostaRespository, AccountClient accountClient){
        this.propostaRespository = propostaRespository;
        this.accountClient = accountClient;
    }

    @Scheduled(fixedDelay = 50000)
    @Transactional
    void associarCartaoComProposta(){

        this.propostaRespository.findByStatusProposta(StatusProposta.SEM_RESTRICAO).forEach(proposta ->{

            ResponseEntity<CartaoResponse> cartaoResponse = this.accountClient.addCartao(new PropostaDto(proposta));
            proposta.vincularCartao(cartaoResponse.getBody().toModel(null));
            this.propostaRespository.save(proposta);

        });
    }

}

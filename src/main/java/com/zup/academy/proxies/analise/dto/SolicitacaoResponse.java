package com.zup.academy.proxies.analise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.academy.proposta.domain.Proposta;


public class SolicitacaoResponse {

    @JsonProperty("documento")
    private String documento;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("resultadoSolicitacao")
    private String resultadoSolicitacao;
    @JsonProperty("idProposta")
    private String idProposta;

    public SolicitacaoResponse(String documento, String nome, String resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public Proposta verificaStatusSolicitacao(Proposta proposta){
        if (this.resultadoSolicitacao.equals("COM_RESTRICAO"))
            proposta.propostaRestrita();
        else
            proposta.propostaSemRestricao();

        return proposta;
    }

}

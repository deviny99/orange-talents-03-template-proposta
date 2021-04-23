package com.zup.academy.proposta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.academy.proposta.domain.Proposta;
import java.util.UUID;

public class PropostaDetalhes {

    private UUID id;
    private String nome;
    private String documento;
    private String status;
    private String numeroCartao;

    public PropostaDetalhes(Proposta proposta){
        this.id = proposta.getId();
        this.nome = proposta.getNome();
        this.documento = proposta.getDocumento();
        this.numeroCartao = proposta.getNumeroCartao();
        this.status = proposta.getStatusProposta().toString();
    }
    /**Construtor utilizado apenas nos modulos de teste da aplicação*/
    @Deprecated
    public PropostaDetalhes() {}

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public UUID getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }
}

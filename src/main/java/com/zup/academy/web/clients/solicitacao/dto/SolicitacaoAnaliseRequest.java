package com.zup.academy.web.clients.solicitacao.dto;

public class SolicitacaoAnaliseRequest {

    private String documento;
    private String nome;
    private String proposta_id;

    public SolicitacaoAnaliseRequest(String documento, String nome, String proposta_id) {
        this.documento = documento;
        this.nome = nome;
        this.proposta_id = proposta_id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getProposta_id() {
        return proposta_id;
    }
}

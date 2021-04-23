package com.zup.academy.proposta.dto;

import com.zup.academy.proposta.domain.Proposta;

public class PropostaDto {

    private String documento;
    private String nome;
    private String idProposta;

    public PropostaDto(Proposta proposta){
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}

package com.zup.academy.proposta.dto;

import com.zup.academy.proposta.domain.Proposta;

public class PropostaDtoFeign {

    private String documento;
    private String nome;
    private String id_Proposta;

    public PropostaDtoFeign(Proposta proposta){
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.id_Proposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return id_Proposta;
    }
}

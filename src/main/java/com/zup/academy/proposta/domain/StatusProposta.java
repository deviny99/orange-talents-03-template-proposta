package com.zup.academy.proposta.domain;

public enum StatusProposta {

    SOLICITADO("Aguardando aprovação..."),
    RESTRITO("Solicitação restrita"),
    SEM_RESTRICAO("Solicitação sem restrição"),
    ASSOCIADO("Proposta aprovada!");

    StatusProposta(String value){
        this.value = value;
    }

    String value;

    @Override
    public String toString() {
        return this.value;
    }
}

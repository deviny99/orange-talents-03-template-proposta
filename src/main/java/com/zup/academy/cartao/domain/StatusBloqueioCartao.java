package com.zup.academy.cartao.domain;

public enum StatusBloqueioCartao {

    BLOQUEADO("BLOQUEADO"),
    NAO_BLOQUEADO("NAO_BLOQUEADO");

    private String value;

    private StatusBloqueioCartao(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

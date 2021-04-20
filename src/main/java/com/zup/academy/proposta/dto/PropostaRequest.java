package com.zup.academy.proposta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.academy.proposta.domain.Proposta;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public class PropostaRequest {

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;
    @NotBlank
    @JsonProperty("nome")
    private String nome;
    @NotBlank
    @JsonProperty("endereco")
    private String endereco;
    @NotNull
    @Positive
    @JsonProperty("salario")
    private BigDecimal salario;
    @Valid
    @JsonProperty("documento")
    private DocumentoRequest documento;

    public PropostaRequest(String email,
                           String nome,
                           String endereco,
                           BigDecimal salario,
                           @Valid DocumentoRequest documento) {
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.documento = documento;
    }

    public Proposta toModel(UUID uuid) {
        return new Proposta(uuid,
                this.nome,
                this.email,
                this.endereco,
                this.documento.getNumeroRegistro(),
                this.salario);
    }

    public String getNumeroRegistro() {
        return documento.getNumeroRegistro();
    }
}
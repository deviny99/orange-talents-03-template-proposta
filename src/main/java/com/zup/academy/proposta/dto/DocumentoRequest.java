package com.zup.academy.proposta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.academy.global.validation.cpf_cnpj.DocumentoGroupSequence;
import com.zup.academy.global.validation.cpf_cnpj.PessoaFisica;
import com.zup.academy.global.validation.cpf_cnpj.PessoaJuridica;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;
import javax.validation.Valid;

@GroupSequenceProvider(value = DocumentoGroupSequence.class)
public class DocumentoRequest implements Documento {

    @CPF(groups = PessoaFisica.class)
    @CNPJ(groups = PessoaJuridica.class)
    private String numeroRegistro;

    public DocumentoRequest(@Valid @JsonProperty("numeroRegistro") String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    @Override
    public String getNumeroRegistro() {
        return this.numeroRegistro;
    }
}

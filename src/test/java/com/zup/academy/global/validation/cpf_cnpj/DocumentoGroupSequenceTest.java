package com.zup.academy.global.validation.cpf_cnpj;

import com.zup.academy.proposta.dto.DocumentoRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testa grupo de documento a ser validado")
public class DocumentoGroupSequenceTest {


    private DocumentoGroupSequence documentoGroupSequence;

    @BeforeEach
    void setup(){
        this.documentoGroupSequence = new DocumentoGroupSequence();
    }

    @Test
    @DisplayName("Deve retornar grupo de pessoa fisica")
    void deveRetornarGrupoDePessoaFisica(){
        var retorno = this.documentoGroupSequence.getValidationGroups(new DocumentoRequest("000.000.000-00")).get(1);
        Assertions.assertTrue(retorno.isAssignableFrom(PessoaFisica.class));
    }

    @Test
    @DisplayName("Deve retornar grupo de pessoa juridica")
    void deveRetornarGrupoDePessoaJuridica(){
        var retorno = this.documentoGroupSequence.getValidationGroups(new DocumentoRequest("00.000.000/0000-00")).get(1);
        Assertions.assertTrue(retorno.isAssignableFrom(PessoaJuridica.class));
    }

    @Test
    @DisplayName("Não deve retornar nenhum grupo")
    void naoDeveRetornarGrupoDePessoa(){
        var sizeCnpjMaior = this.documentoGroupSequence.getValidationGroups(new DocumentoRequest("00.000.000/0000-000")).size();
        Assertions.assertEquals(1,sizeCnpjMaior);
        var sizeCnpjMenor = this.documentoGroupSequence.getValidationGroups(new DocumentoRequest("00.000.000/0000-0")).size();
        Assertions.assertEquals(1,sizeCnpjMenor);
        var sizeCpfMenor = this.documentoGroupSequence.getValidationGroups(new DocumentoRequest("000.000.000-0")).size();
        Assertions.assertEquals(1,sizeCpfMenor);
        var sizeCpfMaior = this.documentoGroupSequence.getValidationGroups(new DocumentoRequest("000.000.000-000")).size();
        Assertions.assertEquals(1,sizeCpfMaior);
    }

}
package com.zup.academy.global.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testa o formatador de CNPJ e CPF")
public class CpfCnpjFormatterTest {

    private final String CPF_FORMATADO = "00000000000";
    private final String CNPJ_FORMATADO = "00000000000000";

    @Test
    @DisplayName("Deve retornar CPF sem caracteres especiais")
    void deveRetornarCpfSemCaracteresEspeciais(){
        var documento = CpfCnpjFormatter.removeCaracteresEspeciais("000.000.000-00");
        Assertions.assertEquals(CPF_FORMATADO,documento);
        var documento2 = CpfCnpjFormatter.removeCaracteresEspeciais("000.000.00000");
        Assertions.assertEquals(CPF_FORMATADO,documento2);
        var documento3 = CpfCnpjFormatter.removeCaracteresEspeciais("000.000000-00");
        Assertions.assertEquals(CPF_FORMATADO,documento3);
        var documento4 = CpfCnpjFormatter.removeCaracteresEspeciais("000000.00000");
        Assertions.assertEquals(CPF_FORMATADO,documento4);
    }

    @Test
    @DisplayName("Deve retornar CNPJ sem caracteres especiais")
    void deveRetornarCnpjSemCaracteresEspeciais(){
        var documento = CpfCnpjFormatter.removeCaracteresEspeciais("00.000.000/0000-00");
        Assertions.assertEquals(CNPJ_FORMATADO,documento);
        var documento2 = CpfCnpjFormatter.removeCaracteresEspeciais("00.000.000/000000");
        Assertions.assertEquals(CNPJ_FORMATADO,documento2);
        var documento3 = CpfCnpjFormatter.removeCaracteresEspeciais("00.0000000000-00");
        Assertions.assertEquals(CNPJ_FORMATADO,documento3);
        var documento4 = CpfCnpjFormatter.removeCaracteresEspeciais("00000.000/000000");
        Assertions.assertEquals(CNPJ_FORMATADO,documento4);
    }


}

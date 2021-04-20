package com.zup.academy.global.validation.cpf_cnpj;

import com.zup.academy.global.utils.CpfCnpjFormatter;
import com.zup.academy.proposta.dto.Documento;
import com.zup.academy.proposta.dto.DocumentoRequest;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import java.util.ArrayList;
import java.util.List;

public class DocumentoGroupSequence implements DefaultGroupSequenceProvider<Documento> {

    private final int PESSOA_FISICA_LENGTH = 11;
    private final int PESSOA_JURIDICA_LENGTH = 14;

    @Override
    public List<Class<?>> getValidationGroups(Documento objeto) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(DocumentoRequest.class);
        if(objeto!=null){

            String valor = CpfCnpjFormatter.removeCaracteresEspeciais(objeto.getNumeroRegistro());

            if(PESSOA_FISICA_LENGTH  == valor.length()){
                groups.add(PessoaFisica.class);
            }
            else if(PESSOA_JURIDICA_LENGTH == valor.length()){
                groups.add(PessoaJuridica.class);
            }
        }
        return groups;
    }
}

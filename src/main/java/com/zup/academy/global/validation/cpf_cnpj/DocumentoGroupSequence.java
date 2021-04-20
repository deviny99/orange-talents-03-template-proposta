package com.zup.academy.global.validation.cpf_cnpj;

import com.zup.academy.proposta.dto.Documento;
import com.zup.academy.proposta.dto.DocumentoRequest;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import java.util.ArrayList;
import java.util.List;
import static com.zup.academy.global.utils.CpfCnpjFormatter.removeCaracteresEspeciais;


public class DocumentoGroupSequence implements DefaultGroupSequenceProvider<Documento> {
    @Override
    public List<Class<?>> getValidationGroups(Documento objeto) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(DocumentoRequest.class);
        if(objeto!=null){

            String valor = removeCaracteresEspeciais(objeto.getNumeroRegistro());

            Integer pessoaFisicia=11;
            Integer pessoaJuridica=14;

            if(pessoaFisicia.equals(valor.length())){
                groups.add(PessoaFisica.class);
            }
            else if(pessoaJuridica.equals(valor.length())){
                groups.add(PessoaJuridica.class);
            }
        }
        return groups;
    }
}

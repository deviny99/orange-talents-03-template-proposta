package com.zup.academy.proposta.domain;

import com.zup.academy.cartao.domain.Cartao;
import com.zup.academy.global.exception.CustomException;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "propostas")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "endereco", nullable = false)
    private String endereco;
    @Column(name = "documento", nullable = false)
    private String documento;
    @Column(name = "salario", nullable = false)
    private BigDecimal salario;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cartao cartao;


    /**Construtor vazio, Unico que utiliza esse construtor é o Hibernate*/
    @Deprecated
    public Proposta(){}

    public Proposta(UUID id, String nome, String email, String endereco, String documento, BigDecimal salario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.documento = documento;
        this.salario = salario;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNumeroCartao() {
        return this.cartao.getNumero();
    }

    public void vincularCartao(Cartao cartao){

        if (cartao==null) {
            throw CustomException.notFound("Não foi informado nenhum cartão para ser vinculado a proposta");
        }

        if(this.cartao == null)
            this.cartao = cartao;
        else
            throw CustomException.unprocessable("A proposta já tem um cartão vinculado a ela, desvincule o " +
                    "cartão atual para poder adicionar outro");
    }
}

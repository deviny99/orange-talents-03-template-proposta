package com.zup.academy.proposta.domain;

import org.hibernate.annotations.GenericGenerator;

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
}

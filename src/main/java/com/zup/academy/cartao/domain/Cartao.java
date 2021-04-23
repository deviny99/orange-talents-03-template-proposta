package com.zup.academy.cartao.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero",nullable = false)
    private String numero;
    @Column(name = "titular",nullable = false)
    private String titular;
    @Column(name = "emitidoEm",nullable = false)
    private LocalDateTime emitidoEm;

    /**Construtor vazio, Unico que utiliza esse construtor é o Hibernate*/
    @Deprecated
    public Cartao(){}

    public Cartao(Long id, String numero, String titular, LocalDateTime emitidoEm) {
        this.id = id;
        this.numero = numero;
        this.titular = titular;
        this.emitidoEm = emitidoEm;
    }

    public String getNumero() {
        return numero;
    }
}

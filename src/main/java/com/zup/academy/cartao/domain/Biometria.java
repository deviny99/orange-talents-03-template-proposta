package com.zup.academy.cartao.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "biometrias")
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "fingerPrint", nullable = false)
    private byte[] fingerPrint;
    @Column(name = "dtEmissao", nullable = false, updatable = false)
    private LocalDateTime dtEmissao = LocalDateTime.now();

    /**Construtor vazio, Unico que utiliza esse construtor é o Hibernate*/
    @Deprecated
    public Biometria(){}

    public Biometria(Long id, byte[] fingerPrint) {
        this.id = id;
        this.fingerPrint = fingerPrint;
    }

    public Long getId() {
        return id;
    }
}
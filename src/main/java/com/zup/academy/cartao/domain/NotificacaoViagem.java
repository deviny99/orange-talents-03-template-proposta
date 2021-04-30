package com.zup.academy.cartao.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
public class NotificacaoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userAgent", nullable = false)
    private String userAgent;
    @Column(name = "ipAddress", nullable = false)
    private String ipAddress;
    @Column(name = "destino", nullable = false)
    private String destino;
    @Column(name = "dtTermino", nullable = false)
    private LocalDate dtTermino;
    @Column(name = "instanteCadastro", updatable = false)
    private LocalDateTime instanteCadastro = LocalDateTime.now();
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Cartao cartao;

    /**Construtor vazio, Unico que utiliza esse construtor é o Hibernate*/
    @Deprecated
    public NotificacaoViagem(){}

    public NotificacaoViagem(Long id, Cartao cartao,String userAgent, String ipAddress, String destino, LocalDate dtTermino) {
        this.id = id;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
        this.destino = destino;
        this.dtTermino = dtTermino;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

}

package com.zup.academy.cartao.domain;

import com.zup.academy.global.exception.CustomException;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bloqueiosCartao")
public class BloqueioCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    private Cartao cartao;
    @Column(name = "instanteBloqueio", updatable = false, nullable = false)
    private LocalDateTime instanteBloqueio = LocalDateTime.now();
    @Column(name = "ipCliente", nullable = false)
    private String ipCliente;
    @Column(name = "userAgent", nullable  = false)
    private String userAgent;

    /**Construtor vazio, Unico que utiliza esse construtor é o Hibernate*/
    @Deprecated
    public BloqueioCartao(){}

    public BloqueioCartao(UUID id, String ipCliente, String userAgent) {
        this.id = id;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    public UUID getId() {
        return id;
    }

    public void bloquearCartao(Cartao cartao){
        if (this.cartao != null){
            throw CustomException.unprocessable("Já tem um cartão vinculado para esse bloqueio,primeiro desvincule o cartao" +
                    " para poder adicionar o cartão atual para esse bloqueio");
        }
        cartao.bloquear();
        this.cartao = cartao;
    }

}

package com.zup.academy.cartao.domain;

import com.zup.academy.global.exception.CustomException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @Fetch(FetchMode.JOIN)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Biometria> biometrias = new HashSet();
    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao = StatusCartao.ATIVO;
    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<CarteiraDigital> carteirasDigitais = new HashSet() ;

    /**Construtor vazio, Unico que utiliza esse construtor é o Hibernate*/
    @Deprecated
    public Cartao(){}

    public Cartao(Long id, String numero, String titular, LocalDateTime emitidoEm) {
        this.id = id;
        this.numero = numero;
        this.titular = titular;
        this.emitidoEm = emitidoEm;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public Set<Biometria> getBiometrias() {
        return biometrias;
    }

    public void vincularBiometria(Biometria biometria){

        if(biometria==null){
            throw CustomException.notFound("Não foi selecionado nenhuma biometria para ser vinculada " +
                    "ao cartao");
        }
        this.biometrias.add(biometria);
    }

    public void bloquear(){

        if (this.statusCartao.equals(StatusCartao.BLOQUEADO)){
            throw CustomException.unprocessable("Esse cartão já está bloqueado");
        }
        this.statusCartao = StatusCartao.BLOQUEADO;
    }

    public void desbloquear(){

        if (this.statusCartao.equals(StatusCartao.ATIVO)){
            throw CustomException.unprocessable("Esse cartão já está desbloqueado");
        }
        this.statusCartao = StatusCartao.ATIVO;
    }

    public void associarCarteira(CarteiraDigital carteiraDigital){

        if (this.carteirasDigitais.contains(carteiraDigital)){
            throw CustomException.unprocessable("Essa carteira digital já está associada para esse cartão");
        }
        this.carteirasDigitais.add(carteiraDigital);
    }
}

package com.zup.academy.cartao.repository;

import com.zup.academy.cartao.domain.BloqueioCartao;
import com.zup.academy.cartao.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloqueioRepository extends JpaRepository<BloqueioCartao,Long> {


    boolean findByCartao(Cartao cartao);

}

package com.zup.academy.cartao.repository;

import com.zup.academy.cartao.domain.BloqueioCartao;
import com.zup.academy.cartao.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloqueioRepository extends JpaRepository<BloqueioCartao, UUID> {


    Boolean existsByCartao(Cartao cartao);

}

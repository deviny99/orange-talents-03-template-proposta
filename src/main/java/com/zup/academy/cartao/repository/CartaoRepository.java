package com.zup.academy.cartao.repository;

import com.zup.academy.cartao.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao,Long> {
}

package com.zup.academy.proposta.repository;

import com.zup.academy.proposta.domain.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PropostaRespository extends JpaRepository<Proposta, UUID> {

    Optional<Proposta> findByDocumento(String documento);

}

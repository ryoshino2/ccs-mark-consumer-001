package com.br.ccs.mark.version.on.ccsmark.repository;

import com.br.ccs.mark.version.on.ccsmark.model.ClienteKafka;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteKafkaRepository extends JpaRepository<ClienteKafka, Long> {
    Page<ClienteKafka> findByNome(String nome, Pageable paginacao);
    ClienteKafka findByIdCliente(Integer idCliente);
}
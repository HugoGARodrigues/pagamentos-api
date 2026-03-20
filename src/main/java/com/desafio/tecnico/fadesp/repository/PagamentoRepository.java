package com.desafio.tecnico.fadesp.repository;

import com.desafio.tecnico.fadesp.entity.Pagamento;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    public boolean existsByCodigoDebito(Integer codigoDebito);
    
}

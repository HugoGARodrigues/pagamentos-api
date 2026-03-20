package com.desafio.tecnico.fadesp.repository;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    public boolean existsByCodigoDebito(Integer codigoDebito);

    @Query("SELECT p FROM Pagamento p"+ 
            " WHERE (:codigoDebito IS NULL OR p.codigoDebito = :codigoDebito)" +
            " AND (:statusPagamento IS NULL OR p.statusPagamento = :statusPagamento)"+
            "AND (:cpfOuCnpjPagador IS NULL OR (p.cpfPagador = :cpfOuCnpjPagador OR p.cnpjPagador = :cpfOuCnpjPagador))"+
            " AND p.ativo = true")
    public Page<Pagamento> listarPaginadoComFiltro(@Param("codigoDebito") Integer codigoDebito, 
                                                   @Param("cpfOuCnpjPagador") String cpfOuCnpjString,
                                                   @Param("statusPagamento") EnumStatusPagamento statusPagamento,
                                                    Pageable pageable);
    
}

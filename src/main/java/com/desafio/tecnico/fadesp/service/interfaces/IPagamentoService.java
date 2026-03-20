package com.desafio.tecnico.fadesp.service.interfaces;

import org.springframework.data.domain.Page;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.rest.dto.request.AtualizarStatusPagamentoRequestDTO;

public interface IPagamentoService {
        public String criarPagamento(com.desafio.tecnico.fadesp.entity.Pagamento pagamento);
        
        public void validarPagamento(com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO pagamentoDTO) throws Exception;

        public Pagamento getPagamentoById(Long id);

        public String atualizarStatusPagamento(AtualizarStatusPagamentoRequestDTO requestDTO) throws Exception;     

        public Page<Pagamento> listarPagamentos(Integer codigoDebito, String cpfPagador, String cnpjPagador, EnumStatusPagamento statusPagamento, Integer paginaAtual, Integer tamanhoPagina, String direcao, String ordenacao);

        public String exclusaoLogica(Long Id);
    }

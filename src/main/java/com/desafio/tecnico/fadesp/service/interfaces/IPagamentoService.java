package com.desafio.tecnico.fadesp.service.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.rest.dto.request.AtualizarStatusPagamentoRequestDTO;

public interface IPagamentoService {
        public String criarPagamento(Pagamento pagamento) throws Exception;
        
        public void validarPagamento(Pagamento pagamento) throws Exception;

        public Pagamento getPagamentoById(UUID id) throws Exception;

        public String atualizarStatusPagamento(AtualizarStatusPagamentoRequestDTO requestDTO) throws Exception;     

        public Page<Pagamento> listarPagamentos(Integer codigoDebito, String cpfPagador, String cnpjPagador, EnumStatusPagamento statusPagamento, Integer paginaAtual, Integer tamanhoPagina, String direcao, String ordenacao);

        public String exclusaoLogica(UUID id) throws Exception;
    }

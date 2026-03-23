package com.desafio.tecnico.fadesp.service.interfaces;

import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;

public interface IPagamentoStrategy {
    void verificaPagamento(String numeroCartao) throws Exception;
}

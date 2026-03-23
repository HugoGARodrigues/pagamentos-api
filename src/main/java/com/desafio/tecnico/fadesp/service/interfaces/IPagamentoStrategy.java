package com.desafio.tecnico.fadesp.service.interfaces;


public interface IPagamentoStrategy {
    void verificaPagamento(String numeroCartao) throws Exception;
}

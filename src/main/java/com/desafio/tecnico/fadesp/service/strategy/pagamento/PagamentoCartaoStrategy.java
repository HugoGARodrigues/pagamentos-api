package com.desafio.tecnico.fadesp.service.strategy.pagamento;

import com.desafio.tecnico.fadesp.service.interfaces.IPagamentoStrategy;

public class PagamentoCartaoStrategy implements IPagamentoStrategy {

    public void verificaPagamento(String numeroCartao) throws Exception {
        if (numeroCartao == null || numeroCartao.isEmpty()) {
            throw new Exception("Número do cartão é obrigatório para pagamentos com cartão.");
        }

    }

}

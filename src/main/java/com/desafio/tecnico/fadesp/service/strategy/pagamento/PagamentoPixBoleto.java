package com.desafio.tecnico.fadesp.service.strategy.pagamento;
import com.desafio.tecnico.fadesp.service.interfaces.IPagamentoStrategy;


public class PagamentoPixBoleto implements IPagamentoStrategy {
    public void verificaPagamento(String numeroCartao) throws Exception{
        if(numeroCartao != null || !numeroCartao.isEmpty()){
                    throw new Exception("Número do cartão deve ser nulo para métodos de pagamento que não sejam cartão.");
                }

    }
}

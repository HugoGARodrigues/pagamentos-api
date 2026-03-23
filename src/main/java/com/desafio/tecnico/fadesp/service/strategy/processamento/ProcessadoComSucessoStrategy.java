package com.desafio.tecnico.fadesp.service.strategy.processamento;

import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.service.interfaces.IProcessamentoStrategy;

public class ProcessadoComSucessoStrategy implements IProcessamentoStrategy {

    @Override
    public void verificaAlteracaoStatus(EnumStatusPagamento statusNovo) throws Exception {
        if (statusNovo == EnumStatusPagamento.PROCESSADO_COM_SUCESSO) {
            throw new Exception("O status do pagamento já está atualizado.");
        } else {
            throw new Exception(
                    "Não é permitido atualizar o status de um pagamento que já foi processado com falha para processado com sucesso.");
        }
    }

}

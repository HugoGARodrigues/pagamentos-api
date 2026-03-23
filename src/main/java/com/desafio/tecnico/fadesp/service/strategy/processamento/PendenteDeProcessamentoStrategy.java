package com.desafio.tecnico.fadesp.service.strategy.processamento;

import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.service.interfaces.IProcessamentoStrategy;

public class PendenteDeProcessamentoStrategy implements IProcessamentoStrategy {

    @Override
    public void verificaAlteracaoStatus(EnumStatusPagamento statusNovo) throws Exception{
        if(statusNovo == EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO){
            throw new Exception("O pagamento se encontra pendente de processamento.");
        }

    }
    
}

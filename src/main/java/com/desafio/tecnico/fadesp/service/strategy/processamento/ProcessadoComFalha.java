package com.desafio.tecnico.fadesp.service.strategy.processamento;

import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.service.interfaces.IProcessamentoStrategy;

public class ProcessadoComFalha implements IProcessamentoStrategy {

    @Override
    public void verificaAlteracaoStatus(EnumStatusPagamento statusNovo) throws Exception{

        if(statusNovo != EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO){
            throw new Exception("Só é permitido alterar o status de processado com falha para Pendente de Processamento");
        }

    }
    
}

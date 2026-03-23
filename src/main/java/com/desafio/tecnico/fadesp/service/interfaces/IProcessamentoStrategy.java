package com.desafio.tecnico.fadesp.service.interfaces;

import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;

public interface IProcessamentoStrategy {
    void verificaAlteracaoStatus(EnumStatusPagamento statusNovo) throws Exception;
}

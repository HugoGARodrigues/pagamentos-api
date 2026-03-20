package com.desafio.tecnico.fadesp.rest.factory;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;
import com.desafio.tecnico.fadesp.rest.dto.response.PagamentoResponseDTO;

public class PagamentoRestFactory {
    public static Pagamento getEntity(PagamentoRequestDTO dto){
        Pagamento pagamento = new Pagamento();
        pagamento.setCodigoDebito(dto.getCodigoDebito());
        pagamento.setCpfPagador(dto.getCpfPagador());
        pagamento.setMetodoPagamento(dto.getMetodoPagamento());
        pagamento.setNumeroCartao(dto.getNumeroCartao());
        pagamento.setValorPagamento(dto.getValorPagamento());
        return pagamento;

    }

    public static PagamentoResponseDTO getDTO(Pagamento pagamento){
        PagamentoResponseDTO dto = new PagamentoResponseDTO();
        dto.setId(pagamento.getId());
        dto.setCodigoDebito(pagamento.getCodigoDebito());
        dto.setCpfPagador(pagamento.getCpfPagador());
        dto.setMetodoPagamento(pagamento.getMetodoPagamento());
        dto.setNumeroCartao(pagamento.getNumeroCartao());
        dto.setStatusPagamento(pagamento.getStatusPagamento());
        dto.setValorPagamento(pagamento.getValorPagamento());
        dto.setAtivo(pagamento.getAtivo());

        return dto;

    }
}

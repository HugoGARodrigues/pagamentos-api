package com.desafio.tecnico.fadesp.rest.factory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;
import com.desafio.tecnico.fadesp.rest.dto.response.MessageResponseDTO;
import com.desafio.tecnico.fadesp.rest.dto.response.PagamentoResponseDTO;

public class PagamentoRestFactory {
    public static Pagamento getEntity(PagamentoRequestDTO dto){
        Pagamento pagamento = new Pagamento();
        pagamento.setCodigoDebito(dto.getCodigoDebito());
        pagamento.setCpfPagador(dto.getCpfPagador());
        pagamento.setCnpjPagador(dto.getCnpjPagador());
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
        dto.setCnpjPagador(pagamento.getCnpjPagador());
        dto.setMetodoPagamento(pagamento.getMetodoPagamento());
        dto.setNumeroCartao(pagamento.getNumeroCartao());
        dto.setStatusPagamento(pagamento.getStatusPagamento());
        dto.setValorPagamento(pagamento.getValorPagamento());
        dto.setAtivo(pagamento.getAtivo());

        return dto;

    }

    public static MessageResponseDTO fromStringToJsonMessagem(String message){
        return new MessageResponseDTO(message);
    }

    public static Page<PagamentoResponseDTO> fromPageEntityToPageDTO(Page<Pagamento> pagamentos){
        List<PagamentoResponseDTO> dtos = pagamentos.map(pagamento -> getDTO(pagamento)).getContent();
        return new PageImpl<>(dtos, pagamentos.getPageable(), pagamentos.getTotalElements());
    }
}

package com.desafio.tecnico.fadesp.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.desafio.tecnico.fadesp.enums.EnumMetodoPagamento;
import com.desafio.tecnico.fadesp.repository.PagamentoRepository;
import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;
import com.desafio.tecnico.fadesp.service.interfaces.IPagamentoStrategy;
import com.desafio.tecnico.fadesp.service.strategy.pagamento.PagamentoCartaoStrategy;
import com.desafio.tecnico.fadesp.service.strategy.pagamento.PagamentoPixBoleto;

public class ValidacaoUtil {


    @Autowired
    private PagamentoRepository pagamentoRepository;

    private final Map<EnumMetodoPagamento, IPagamentoStrategy> mapPagamentoStrategy = Map.of(
        EnumMetodoPagamento.PIX, new PagamentoPixBoleto(),
        EnumMetodoPagamento.BOLETO, new PagamentoPixBoleto(),
        EnumMetodoPagamento.CARTAO_CREDITO, new PagamentoCartaoStrategy(),
        EnumMetodoPagamento.CARTAO_DEBITO, new PagamentoCartaoStrategy()

    );

    public void validarPagamento(PagamentoRequestDTO pagamentoDTO) throws Exception {
        
        if( pagamentoDTO.getValorPagamento().compareTo(java.math.BigDecimal.ZERO) <= 0){
            throw new Exception("Valor do pagamento deve ser maior que zero.");
        }

        if(pagamentoDTO.getMetodoPagamento() == null){
            throw new Exception("O método de pagamento é obrigatório");
        }else{
            mapPagamentoStrategy.get(pagamentoDTO.getMetodoPagamento()).verificaPagamento(pagamentoDTO.getNumeroCartao());
        }

    }



    //public void validarCpf(String cpf){

    //}

    //public void validarCnpj(String cnpj
         
    //){

    //}




}
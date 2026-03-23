package com.desafio.tecnico.fadesp.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumMetodoPagamento;
import com.desafio.tecnico.fadesp.service.interfaces.IPagamentoStrategy;
import com.desafio.tecnico.fadesp.service.strategy.pagamento.PagamentoCartaoStrategy;
import com.desafio.tecnico.fadesp.service.strategy.pagamento.PagamentoPixBoleto;

@Component
public class ValidacaoUtil {

    private final Map<EnumMetodoPagamento, IPagamentoStrategy> mapPagamentoStrategy = Map.of(
        EnumMetodoPagamento.PIX, new PagamentoPixBoleto(),
        EnumMetodoPagamento.BOLETO, new PagamentoPixBoleto(),
        EnumMetodoPagamento.CARTAO_CREDITO, new PagamentoCartaoStrategy(),
        EnumMetodoPagamento.CARTAO_DEBITO, new PagamentoCartaoStrategy()

    );

    public void validarPagamento(Pagamento entity) throws Exception {
        
        if( entity.getValorPagamento().compareTo(java.math.BigDecimal.ZERO) <= 0){
            throw new Exception("Valor do pagamento deve ser maior que zero.");
        }

        if(entity.getMetodoPagamento() == null){
            throw new Exception("O método de pagamento é obrigatório");
        }else{
            mapPagamentoStrategy.get(entity.getMetodoPagamento()).verificaPagamento(entity.getNumeroCartao());
        }

    }



    public void validarCpf(String cpf) throws Exception{
        if(cpf.length() != 11){
            throw new Exception("O Cpf deve ter 11 números");
        }
    }

    public void validarCnpj(String cnpj)throws Exception{
        if(cnpj.length() != 14){
            throw new Exception("O Cnpj deve ter 14 números");
        }
    }

    public void validaCpfOuCnpj(Pagamento pagamento) throws Exception{
        if (!pagamento.getCpfPagador().isBlank() && !pagamento.getCnpjPagador().isBlank()){
            throw new Exception("O pagamento não pode ser feito por uma pessoa física e uma pessoa jurídica ao mesmo tempo");
        }

        if (pagamento.getCnpjPagador().isBlank()) {
            validarCpf(pagamento.getCpfPagador());
        }else{
            validarCnpj(pagamento.getCnpjPagador());
        }
    }




}
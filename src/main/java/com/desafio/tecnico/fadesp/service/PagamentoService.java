package com.desafio.tecnico.fadesp.service;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumMetodoPagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.repository.PagamentoRepository;
import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;



@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Transactional
    public String criarPagamento(Pagamento pagamento){

        pagamento.setStatusPagamento(EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO);
        pagamento.setAtivo(true);

        pagamentoRepository.save(pagamento);
        return "Pagamento salvo com sucesso.";


    }

    public void validarPagamento(PagamentoRequestDTO pagamentoDTO) throws Exception {
        if( pagamentoDTO.getValorPagamento().compareTo(java.math.BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero.");
        }

        if(pagamentoDTO.getMetodoPagamento() != null){
            if(pagamentoDTO.getMetodoPagamento() == EnumMetodoPagamento.CARTAO_CREDITO || pagamentoDTO.getMetodoPagamento() == EnumMetodoPagamento.CARTAO_DEBITO){
                if(pagamentoDTO.getNumeroCartao() == null || pagamentoDTO.getNumeroCartao().isEmpty()){
                    throw new Exception("Número do cartão é obrigatório para pagamentos com cartão.");
                }
            }else{
                if(pagamentoDTO.getNumeroCartao() != null && !pagamentoDTO.getNumeroCartao().isEmpty()){
                    throw new Exception("Número do cartão deve ser nulo ou vazio para métodos de pagamento que não sejam cartão.");
                }
            }
        }else{
            throw new IllegalArgumentException("Método de pagamento é obrigatório.");

        }

        if(pagamentoRepository.existsByCodigoDebito(pagamentoDTO.getCodigoDebito())){
            throw new IllegalArgumentException("Este pagamento já existe em nosso sistema.");
        }

        
    }

    public Pagamento getPagamentoById(Long id){
        return pagamentoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado."));
    }
    
}

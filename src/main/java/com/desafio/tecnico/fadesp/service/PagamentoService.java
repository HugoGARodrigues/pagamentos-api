package com.desafio.tecnico.fadesp.service;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.repository.PagamentoRepository;
import com.desafio.tecnico.fadesp.rest.dto.request.AtualizarStatusPagamentoRequestDTO;
import com.desafio.tecnico.fadesp.service.interfaces.IPagamentoService;
import com.desafio.tecnico.fadesp.service.interfaces.IProcessamentoStrategy;
import com.desafio.tecnico.fadesp.service.strategy.processamento.PendenteDeProcessamentoStrategy;
import com.desafio.tecnico.fadesp.service.strategy.processamento.ProcessadoComFalhaStrategy;
import com.desafio.tecnico.fadesp.service.strategy.processamento.ProcessadoComSucessoStrategy;
import com.desafio.tecnico.fadesp.util.ValidacaoUtil;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService implements IPagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ValidacaoUtil validador;

    private final Map<EnumStatusPagamento, IProcessamentoStrategy> mapProcessamentoStrategy = Map.of(
            EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO, new PendenteDeProcessamentoStrategy(),
            EnumStatusPagamento.PROCESSADO_COM_FALHA, new ProcessadoComFalhaStrategy(),
            EnumStatusPagamento.PROCESSADO_COM_SUCESSO, new ProcessadoComSucessoStrategy()

    );

    @Transactional
    @Override
    public String criarPagamento(Pagamento pagamento) throws Exception {

        pagamento.setStatusPagamento(EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO);
        pagamento.setAtivo(true);

        pagamentoRepository.save(pagamento);
        return "Pagamento do boleto " + pagamento.getCodigoDebito() + " salvo com sucesso.";

    }

    @Override
    public void validarPagamento(Pagamento pagamento) throws Exception {
        if (pagamento.getValorPagamento().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor do pagamento deve ser maior que zero.");
        }

        validador.validaCpfOuCnpj(pagamento);

        validador.validarPagamento(pagamento);

        if (pagamento.getMetodoPagamento() == null) {
            throw new Exception("Método de pagamento é obrigatório.");
        }

        if(pagamentoRepository.existsByCodigoDebito(pagamento.getCodigoDebito())){
            throw new Exception("Um pagamento com este código de débito já foi cadastrado em nosso sistema");
        }

    }

    @Override
    public Pagamento getPagamentoById(UUID id) throws Exception {
        return pagamentoRepository.findById(id).orElseThrow(() -> new Exception("Pagamento não encontrado."));
    }

    @Transactional
    @Override
    public String atualizarStatusPagamento(AtualizarStatusPagamentoRequestDTO dto) throws Exception {

        if (dto.getNovoStatusPagamento() == null) {
            throw new Exception("Status do pagamento está vazio e ele é obrigatório.");
        }
        if (dto.getIdPagamento() == null) {
            throw new Exception("ID do pagamento é obrigatório para atualização de status.");
        }
        if (!pagamentoRepository.existsById(dto.getIdPagamento())) {
            throw new Exception("Pagamento não encontrado para o ID fornecido.");
        }

        Pagamento pagamentoASerAtualizado = getPagamentoById(dto.getIdPagamento());

        mapProcessamentoStrategy.get(pagamentoASerAtualizado.getStatusPagamento())
                .verificaAlteracaoStatus(dto.getNovoStatusPagamento());

        pagamentoASerAtualizado.setStatusPagamento(dto.getNovoStatusPagamento());
        pagamentoRepository.save(pagamentoASerAtualizado);
        return "Status do pagamento " + pagamentoASerAtualizado.getId() + " atualizado com sucesso.";
    }

    @Override
    public Page<Pagamento> listarPagamentos(Integer codigoDebito, String cpfPagador, String cnpjPagador,
            EnumStatusPagamento statusPagamento, Integer paginaAtual, Integer tamanhoPagina, String direcao,
            String ordenacao) {
        Sort sort = Sort.by(Sort.Direction.fromString(direcao), ordenacao);
        Pageable pageable = PageRequest.of(paginaAtual, tamanhoPagina, sort);
        String cpfOuCnpjString = (cpfPagador != null && !cpfPagador.isEmpty()) ? cpfPagador : cnpjPagador;

        Page<Pagamento> pagamentoPaginado = pagamentoRepository.listarPaginadoComFiltro(codigoDebito, cpfOuCnpjString,
                statusPagamento, pageable);
        return pagamentoPaginado;
    }

    @Transactional
    @Override
    public String exclusaoLogica(UUID id) throws Exception {
        if (!pagamentoRepository.existsById(id)) {
            throw new Exception("Pagamento não encontrado para o ID fornecido.");
        }
        Pagamento pagamento = getPagamentoById(id);
        if (pagamento.getStatusPagamento() == EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO) {
            pagamento.setAtivo(false);
            pagamentoRepository.save(pagamento);
            return "Pagamento " + id + " excluído com sucesso.";
        } else {
            throw new Exception("Não é permitido excluir um pagamento que já foi processado.");
        }

    }
}
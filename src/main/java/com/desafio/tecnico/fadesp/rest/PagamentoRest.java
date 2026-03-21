package com.desafio.tecnico.fadesp.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.rest.dto.request.AtualizarStatusPagamentoRequestDTO;
import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;
import com.desafio.tecnico.fadesp.rest.dto.response.MessageResponseDTO;
import com.desafio.tecnico.fadesp.rest.dto.response.PagamentoResponseDTO;
import com.desafio.tecnico.fadesp.rest.factory.PagamentoRestFactory;
import com.desafio.tecnico.fadesp.service.interfaces.IPagamentoService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RequestMapping("/pagamentos")
@RestController
public class PagamentoRest {

    @Autowired
    private IPagamentoService pagamentoService;

    @Operation(summary = "Recebe um pagamento e o salva no banco de dados do sistema", description = "Endpoint para recebimento de dados de pagamento")
    @PostMapping("/receber-pagamento")
    public ResponseEntity<MessageResponseDTO> criarPagamento(@RequestBody PagamentoRequestDTO pagamentoDTO)
            throws Exception {
        pagamentoService.validarPagamento(pagamentoDTO);
        Pagamento pagamento = PagamentoRestFactory.getEntity(pagamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PagamentoRestFactory.fromStringToJsonMessagem(pagamentoService.criarPagamento(pagamento)));
    }

    @Operation(summary = "Recebe o id de um pagamento e o novo status desse pagamento para atualização no sistema", description = "Endpoint para receber dados para atualizar um pagamento")
    @PutMapping("/atualizar-pagamento")
    public ResponseEntity<MessageResponseDTO> atualizaPagamento(@RequestBody AtualizarStatusPagamentoRequestDTO dto)
            throws Exception {

        return ResponseEntity
                .ok(PagamentoRestFactory.fromStringToJsonMessagem(pagamentoService.atualizarStatusPagamento(dto)));
    }

    @Operation(summary = "Lista paginada que recebe como parâmetros de filtragem, código debito, cpf ou cnpj do pagador e status do pagamento, além de filtragem também recebe parâmetros para a formatação dessa página.", description = "endpoint que retorna uma lista paginada que aceita filtragem por codigo de débito, cpf ou cnpj do pagador e status do pagamento")
    @GetMapping("/lista-paginada")
    public ResponseEntity<Page<PagamentoResponseDTO>> listarPaginada(
            @RequestParam(required = false) Integer codigoDebito,
            @RequestParam(required = false) String cpfPagador,
            @RequestParam(required = false) String cnpjPagador,
            @RequestParam(required = false) EnumStatusPagamento statusPagamento,
            @RequestParam(required = false, defaultValue = "0") Integer paginaAtual,
            @RequestParam(required = false, defaultValue = "10") Integer tamanhoPagina,
            @RequestParam(required = false, defaultValue = "asc") String direcao,
            @RequestParam(required = false, defaultValue = "id") String ordenacao) throws Exception {
        return ResponseEntity
                .ok(PagamentoRestFactory.fromPageEntityToPageDTO(pagamentoService.listarPagamentos(codigoDebito,
                        cpfPagador, cnpjPagador, statusPagamento, paginaAtual, tamanhoPagina, direcao, ordenacao)));
    }

    @Operation(summary = "Recebe o id de um pagamento para exclusão, desde que o status desse pagamento seja Pendente de Processamento", description = "Endpoint que recebe o id de um pagamento para executar uma exclusão desde que o pagamento esteja com o status PENDENTE_DE_PROCESSAMENTO")
    @DeleteMapping("excluir-pagamento/{id}")
    public ResponseEntity<MessageResponseDTO> exclusaoLogicaPagamento(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(PagamentoRestFactory.fromStringToJsonMessagem(pagamentoService.exclusaoLogica(id)));
    }

}

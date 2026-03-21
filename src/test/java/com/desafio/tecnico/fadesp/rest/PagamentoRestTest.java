package com.desafio.tecnico.fadesp.rest;

import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.rest.dto.request.AtualizarStatusPagamentoRequestDTO;
import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;
import com.desafio.tecnico.fadesp.service.interfaces.IPagamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagamentoRest.class)
class PagamentoRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IPagamentoService pagamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST - Deve receber pagamento e retornar 201")
    void deveCriarPagamento() throws Exception {
        PagamentoRequestDTO dto = new PagamentoRequestDTO();
        dto.setCodigoDebito(100);

        when(pagamentoService.criarPagamento(any())).thenReturn("Sucesso");

        mockMvc.perform(post("/pagamentos/receber-pagamento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("GET - Deve listar pagamentos paginados")
    void deveListarPaginado() throws Exception {
        Page<com.desafio.tecnico.fadesp.entity.Pagamento> page = new PageImpl<>(Collections.emptyList());
        
        when(pagamentoService.listarPagamentos(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(page);

        mockMvc.perform(get("/pagamentos/lista-paginada")
                .param("paginaAtual", "0")
                .param("tamanhoPagina", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT - Deve atualizar status")
    void deveAtualizarStatus() throws Exception {
        AtualizarStatusPagamentoRequestDTO dto = new AtualizarStatusPagamentoRequestDTO();
        dto.setIdPagamento(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        dto.setNovoStatusPagamento(EnumStatusPagamento.PROCESSADO_COM_SUCESSO);

        when(pagamentoService.atualizarStatusPagamento(any())).thenReturn("Atualizado");

        mockMvc.perform(put("/pagamentos/atualizar-pagamento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE - Deve realizar exclusão lógica via ID na URL")
    void deveExcluirPagamento() throws Exception {
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        when(pagamentoService.exclusaoLogica(id)).thenReturn("Excluído");

        mockMvc.perform(delete("/pagamentos/excluir-pagamento/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }
}
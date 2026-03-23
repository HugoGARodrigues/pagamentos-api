package com.desafio.tecnico.fadesp.service;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumMetodoPagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.repository.PagamentoRepository;
import com.desafio.tecnico.fadesp.rest.dto.request.AtualizarStatusPagamentoRequestDTO;
import com.desafio.tecnico.fadesp.util.ValidacaoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ValidacaoUtil validador; 

    @InjectMocks
    private PagamentoService pagamentoService;

    private Pagamento pagamentoExemplo;
    private final UUID ID_EXEMPLO = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    @BeforeEach
    void setUp() {
        pagamentoExemplo = new Pagamento();
        pagamentoExemplo.setId(ID_EXEMPLO);
        pagamentoExemplo.setCodigoDebito(123);
        pagamentoExemplo.setValorPagamento(BigDecimal.TEN);
        pagamentoExemplo.setMetodoPagamento(EnumMetodoPagamento.BOLETO);
        pagamentoExemplo.setStatusPagamento(EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO);
        pagamentoExemplo.setAtivo(true);
    }

    @Test
    @DisplayName("Deve criar um pagamento com status PENDENTE e Ativo")
    void deveCriarPagamentoComSucesso() throws Exception {
        String resultado = pagamentoService.criarPagamento(pagamentoExemplo);

        verify(pagamentoRepository, times(1)).save(pagamentoExemplo);
        assertThat(resultado).contains("salvo com sucesso");
        assertThat(pagamentoExemplo.getStatusPagamento()).isEqualTo(EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO);
        assertThat(pagamentoExemplo.getAtivo()).isTrue();
    }

    @Test
    @DisplayName("Deve lançar exceção quando valor for zero ou negativo na validação")
    void deveLancarExcecaoValorInvalido() {
        pagamentoExemplo.setValorPagamento(BigDecimal.ZERO);

        assertThatThrownBy(() -> pagamentoService.validarPagamento(pagamentoExemplo))
                .isInstanceOf(Exception.class)
                .hasMessage("Valor do pagamento deve ser maior que zero.");
    }

    @Test
    @DisplayName("Deve lançar exceção se método de pagamento for nulo")
    void deveLancarExcecaoMetodoPagamentoNulo() {
        pagamentoExemplo.setMetodoPagamento(null);

        assertThatThrownBy(() -> pagamentoService.validarPagamento(pagamentoExemplo))
                .isInstanceOf(Exception.class)
                .hasMessage("Método de pagamento é obrigatório.");
    }

    @Test
    @DisplayName("Deve atualizar status de PENDENTE para SUCESSO via Strategy")
    void deveAtualizarStatusComSucesso() throws Exception {
        AtualizarStatusPagamentoRequestDTO dto = new AtualizarStatusPagamentoRequestDTO();
        dto.setIdPagamento(ID_EXEMPLO);
        dto.setNovoStatusPagamento(EnumStatusPagamento.PROCESSADO_COM_SUCESSO);

        when(pagamentoRepository.existsById(ID_EXEMPLO)).thenReturn(true);
        when(pagamentoRepository.findById(ID_EXEMPLO)).thenReturn(Optional.of(pagamentoExemplo));

        String resultado = pagamentoService.atualizarStatusPagamento(dto);

        assertThat(resultado).contains("atualizado com sucesso");
        assertThat(pagamentoExemplo.getStatusPagamento()).isEqualTo(EnumStatusPagamento.PROCESSADO_COM_SUCESSO);
        verify(pagamentoRepository).save(pagamentoExemplo);
    }

    @Test
@DisplayName("Não deve permitir atualizar pagamento que já está com status PROCESSADO_COM_SUCESSO")
void naoDeveAtualizarPagamentoJaFinalizado() {
    pagamentoExemplo.setStatusPagamento(EnumStatusPagamento.PROCESSADO_COM_SUCESSO);

    AtualizarStatusPagamentoRequestDTO dto = new AtualizarStatusPagamentoRequestDTO();
    dto.setIdPagamento(ID_EXEMPLO);

    dto.setNovoStatusPagamento(EnumStatusPagamento.PROCESSADO_COM_FALHA);

    when(pagamentoRepository.existsById(ID_EXEMPLO)).thenReturn(true);
    when(pagamentoRepository.findById(ID_EXEMPLO)).thenReturn(Optional.of(pagamentoExemplo));

    assertThatThrownBy(() -> pagamentoService.atualizarStatusPagamento(dto))
            .isInstanceOf(Exception.class) 
            .hasMessageContaining("Não é permitido atualizar o status de um pagamento que já foi processado com falha para processado com sucesso.");
}

    @Test
    @DisplayName("Deve realizar exclusão lógica apenas se status for PENDENTE")
    void deveExcluirLogicamente() throws Exception {
        when(pagamentoRepository.existsById(ID_EXEMPLO)).thenReturn(true);
        when(pagamentoRepository.findById(ID_EXEMPLO)).thenReturn(Optional.of(pagamentoExemplo));

        String resultado = pagamentoService.exclusaoLogica(ID_EXEMPLO);

        assertThat(pagamentoExemplo.getAtivo()).isFalse();
        assertThat(resultado).contains("excluído com sucesso");
        verify(pagamentoRepository).save(pagamentoExemplo);
    }

    @Test
    @DisplayName("Não deve permitir exclusão lógica de pagamentos processados")
    void naoDeveExcluirPagamentoProcessado() {
        pagamentoExemplo.setStatusPagamento(EnumStatusPagamento.PROCESSADO_COM_FALHA);

        when(pagamentoRepository.existsById(ID_EXEMPLO)).thenReturn(true);
        when(pagamentoRepository.findById(ID_EXEMPLO)).thenReturn(Optional.of(pagamentoExemplo));

        assertThatThrownBy(() -> pagamentoService.exclusaoLogica(ID_EXEMPLO))
                .isInstanceOf(Exception.class)
                .hasMessage("Não é permitido excluir um pagamento que já foi processado.");
    }
}
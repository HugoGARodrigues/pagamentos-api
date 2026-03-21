package com.desafio.tecnico.fadesp.service;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumMetodoPagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.repository.PagamentoRepository;
import com.desafio.tecnico.fadesp.rest.dto.request.AtualizarStatusPagamentoRequestDTO;
import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    private Pagamento pagamentoExemplo;

    @BeforeEach
    void setUp() {
        pagamentoExemplo = new Pagamento();
        pagamentoExemplo.setId(1L);
        pagamentoExemplo.setCodigoDebito(123);
        pagamentoExemplo.setStatusPagamento(EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO);
        pagamentoExemplo.setAtivo(true);
    }

    @Test
    @DisplayName("Deve criar um pagamento com sucesso")
    void deveCriarPagamentoComSucesso() {
        String resultado = pagamentoService.criarPagamento(pagamentoExemplo);

        verify(pagamentoRepository, times(1)).save(pagamentoExemplo);
        assertThat(resultado).contains("salvo com sucesso");
        assertThat(pagamentoExemplo.getStatusPagamento()).isEqualTo(EnumStatusPagamento.PENDENTE_DE_PROCESSAMENTO);
        assertThat(pagamentoExemplo.getAtivo()).isTrue();
    }

    @Test
    @DisplayName("Deve lançar exceção quando valor for zero ou negativo")
    void deveLancarExcecaoValorInvalido() {
        PagamentoRequestDTO dto = new PagamentoRequestDTO();
        dto.setValorPagamento(BigDecimal.ZERO);

        assertThatThrownBy(() -> pagamentoService.validarPagamento(dto))
                .isInstanceOf(Exception.class)
                .hasMessage("Valor do pagamento deve ser maior que zero.");
    }

    @Test
    @DisplayName("Deve exigir número do cartão para método CARTAO_CREDITO")
    void deveExigirCartaoParaMetodoCredito() {
        PagamentoRequestDTO dto = new PagamentoRequestDTO();
        dto.setValorPagamento(BigDecimal.TEN);
        dto.setMetodoPagamento(EnumMetodoPagamento.CARTAO_CREDITO);
        dto.setNumeroCartao("");

        assertThatThrownBy(() -> pagamentoService.validarPagamento(dto))
                .isInstanceOf(Exception.class)
                .hasMessage("Número do cartão é obrigatório para pagamentos com cartão.");
    }

    @Test
    @DisplayName("Deve atualizar status com sucesso")
    void deveAtualizarStatusComSucesso() throws Exception {
        AtualizarStatusPagamentoRequestDTO dto = new AtualizarStatusPagamentoRequestDTO();
        dto.setIdPagamento(1L);
        dto.setNovoStatusPagamento(EnumStatusPagamento.PROCESSADO_COM_SUCESSO);

        when(pagamentoRepository.existsById(1L)).thenReturn(true);
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamentoExemplo));

        String resultado = pagamentoService.atualizarStatusPagamento(dto);

        assertThat(resultado).contains("atualizado com sucesso");
        assertThat(pagamentoExemplo.getStatusPagamento()).isEqualTo(EnumStatusPagamento.PROCESSADO_COM_SUCESSO);
        verify(pagamentoRepository).save(any());
    }

    @Test
    @DisplayName("Não deve permitir atualizar pagamento já processado com sucesso")
    void naoDeveAtualizarPagamentoJaFinalizado() {
        pagamentoExemplo.setStatusPagamento(EnumStatusPagamento.PROCESSADO_COM_SUCESSO);

        AtualizarStatusPagamentoRequestDTO dto = new AtualizarStatusPagamentoRequestDTO();
        dto.setIdPagamento(1L);
        dto.setNovoStatusPagamento(EnumStatusPagamento.PROCESSADO_COM_FALHA);

        when(pagamentoRepository.existsById(1L)).thenReturn(true);
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamentoExemplo));

        assertThatThrownBy(() -> pagamentoService.atualizarStatusPagamento(dto))
                .isInstanceOf(Exception.class)
                .hasMessage("Não é permitido atualizar o status de um pagamento que já foi processado com sucesso.");
    }

    @Test
    @DisplayName("Deve realizar exclusão lógica de pagamento pendente")
    void deveExcluirLogicamente() throws Exception {
        when(pagamentoRepository.existsById(1L)).thenReturn(true);
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamentoExemplo));

        String resultado = pagamentoService.exclusaoLogica(1L);

        assertThat(pagamentoExemplo.getAtivo()).isFalse();
        assertThat(resultado).contains("excluído com sucesso");
        verify(pagamentoRepository).save(pagamentoExemplo);
    }

    @Test
    @DisplayName("Não deve excluir pagamento que não está mais pendente")
    void naoDeveExcluirPagamentoProcessado() {
        pagamentoExemplo.setStatusPagamento(EnumStatusPagamento.PROCESSADO_COM_SUCESSO);

        when(pagamentoRepository.existsById(1L)).thenReturn(true);
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamentoExemplo));

        assertThatThrownBy(() -> pagamentoService.exclusaoLogica(1L))
                .isInstanceOf(Exception.class)
                .hasMessage("Não é permitido excluir um pagamento que já foi processado.");
    }

}
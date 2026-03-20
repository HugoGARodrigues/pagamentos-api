package com.desafio.tecnico.fadesp.rest.dto.response;

import java.math.BigDecimal;

import com.desafio.tecnico.fadesp.enums.EnumMetodoPagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoResponseDTO {
    private Long id;
    private Integer codigoDebito;
    private String cpfPagador;
    private EnumMetodoPagamento metodoPagamento;
    private String numeroCartao;
    private EnumStatusPagamento statusPagamento;
    private BigDecimal valorPagamento;
    private Boolean ativo;
    

}

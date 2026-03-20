package com.desafio.tecnico.fadesp.rest.dto.request;

import com.desafio.tecnico.fadesp.enums.EnumMetodoPagamento;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequestDTO {
    private Integer codigoDebito;
    private String cpfPagador;
    private String cnpjPagador;
    private EnumMetodoPagamento metodoPagamento;
    private String numeroCartao;
    private BigDecimal valorPagamento;
    


}
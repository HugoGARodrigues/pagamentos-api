package com.desafio.tecnico.fadesp.rest.dto.request;

import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarStatusPagamentoRequestDTO {
    private Long idPagamento;
    private EnumStatusPagamento novoStatusPagamento;

}

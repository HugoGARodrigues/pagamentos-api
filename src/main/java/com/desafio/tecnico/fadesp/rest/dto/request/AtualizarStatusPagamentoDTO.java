package com.desafio.tecnico.fadesp.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarStatusPagamentoDTO {
    private Long idPagamento;
    private String statusPagamento;
}

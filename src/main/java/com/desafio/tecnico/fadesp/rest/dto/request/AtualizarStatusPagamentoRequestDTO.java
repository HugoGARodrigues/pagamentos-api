package com.desafio.tecnico.fadesp.rest.dto.request;

import java.util.UUID;

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
    private UUID idPagamento;
    private EnumStatusPagamento novoStatusPagamento;

}

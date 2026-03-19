package com.desafio.tecnico.fadesp.enums;

public enum EnumMetodoPagamento {
    BOLETO("Boleto"),
    PIX("PIX"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito");

    private String label;
    
    
    EnumMetodoPagamento(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

}

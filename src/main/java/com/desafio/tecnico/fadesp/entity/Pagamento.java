package com.desafio.tecnico.fadesp.entity;

import java.math.BigDecimal;

import com.desafio.tecnico.fadesp.entity.Pagamento;

import com.desafio.tecnico.fadesp.enums.EnumMetodoPagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento", schema = "public")
public class Pagamento {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_debito", nullable = false)
    private Integer codigoDebito;

    @Column(name = "cpf_pagador")
    private String cpfPagador;

    @Column(name = "cnpj_pagador")
    private String cnpjPagador;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private EnumMetodoPagamento metodoPagamento;

    @Column(name = "numero_cartao")
    private String numeroCartao;

    @Column(name = "valor_pagamento",precision = 19, scale = 4, nullable = false)
    private BigDecimal valorPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private EnumStatusPagamento statusPagamento;

    @Column(name = "ativo")
    private Boolean ativo;

    public Pagamento() {
    }

    public Pagamento(Integer codigoDebito, String cpfPagador, String cnpjPagador, EnumMetodoPagamento metodoPagamento, String numeroCartao , BigDecimal valorPagamento, EnumStatusPagamento statusPagamento, Boolean ativo) {
        this.codigoDebito = codigoDebito;
        this.cpfPagador = cpfPagador;
        this.cnpjPagador = cnpjPagador;
        this.metodoPagamento = metodoPagamento;
        this.numeroCartao = numeroCartao;
        this.valorPagamento = valorPagamento;
        this.statusPagamento = statusPagamento;
        this.ativo = ativo;

    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoDebito() {
        return codigoDebito;
    }

    public void setCodigoDebito(Integer codigoDebito) {
        this.codigoDebito = codigoDebito;
    }

    public String getCpfPagador() {
        return cpfPagador;
    }

    public void setCpfPagador(String cpfPagador) {
        this.cpfPagador = cpfPagador;
    }

    public String getCnpjPagador() {
        return cnpjPagador;
    }

    public void setCnpjPagador(String cnpjPagador) {
        this.cnpjPagador = cnpjPagador;
    }

    public EnumMetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(EnumMetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }  

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public BigDecimal getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(BigDecimal valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public EnumStatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(EnumStatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}

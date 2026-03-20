package com.desafio.tecnico.fadesp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;
import com.desafio.tecnico.fadesp.rest.dto.response.PagamentoResponseDTO;
import com.desafio.tecnico.fadesp.rest.factory.PagamentoRestFactory;
import com.desafio.tecnico.fadesp.service.PagamentoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping("/pagamentos")
@RestController
public class PagamentoRest {
    
    @Autowired
    private PagamentoService pagamentoService;


    @PostMapping("/receber-pagamento")
    public ResponseEntity<String> criarPagamento(@RequestBody PagamentoRequestDTO pagamentoDTO) throws Exception { 
        pagamentoService.validarPagamento(pagamentoDTO);
        Pagamento pagamento = PagamentoRestFactory.getEntity(pagamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.criarPagamento(pagamento));
    }

    @GetMapping("/{id}")
    public PagamentoResponseDTO getPagamentoById(@PathVariable Long id) {
        return PagamentoRestFactory.getDTO(pagamentoService.getPagamentoById(id));
    }
    
    

}

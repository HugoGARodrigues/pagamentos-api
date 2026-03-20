package com.desafio.tecnico.fadesp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.tecnico.fadesp.entity.Pagamento;
import com.desafio.tecnico.fadesp.enums.EnumStatusPagamento;
import com.desafio.tecnico.fadesp.rest.dto.request.AtualizarStatusPagamentoRequestDTO;
import com.desafio.tecnico.fadesp.rest.dto.request.PagamentoRequestDTO;
import com.desafio.tecnico.fadesp.rest.dto.response.MessageResponseDTO;
import com.desafio.tecnico.fadesp.rest.dto.response.PagamentoResponseDTO;
import com.desafio.tecnico.fadesp.rest.factory.PagamentoRestFactory;
import com.desafio.tecnico.fadesp.service.PagamentoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RequestMapping("/pagamentos")
@RestController
public class PagamentoRest {
    
    @Autowired
    private PagamentoService pagamentoService;


    @PostMapping("/receber-pagamento")
    public ResponseEntity<MessageResponseDTO> criarPagamento(@RequestBody PagamentoRequestDTO pagamentoDTO) throws Exception { 
        pagamentoService.validarPagamento(pagamentoDTO);
        Pagamento pagamento = PagamentoRestFactory.getEntity(pagamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(PagamentoRestFactory.fromStringToJsonMessagem(pagamentoService.criarPagamento(pagamento)))    ;
    }

    @GetMapping("/{id}")
    public PagamentoResponseDTO getPagamentoById(@PathVariable Long id) {
        return PagamentoRestFactory.getDTO(pagamentoService.getPagamentoById(id));
    }

    @PutMapping("/atualizar-pagamento")
    public ResponseEntity<MessageResponseDTO> atualizaPagamento(@RequestBody AtualizarStatusPagamentoRequestDTO dto) throws Exception{

        return ResponseEntity.ok(PagamentoRestFactory.fromStringToJsonMessagem(pagamentoService.atualizarStatusPagamento(dto)));
    }
    
   @GetMapping("/lista-paginada")
   public ResponseEntity<Page<PagamentoResponseDTO>> listarPaginada(@RequestParam(required = false) Integer codigoDebito,
                                                                    @RequestParam(required = false) String cpfPagador,
                                                                    @RequestParam(required = false) String cnpjPagador,
                                                                    @RequestParam(required = false) EnumStatusPagamento statusPagamento,
                                                                    @RequestParam(required = false, defaultValue = "0") Integer paginaAtual,
                                                                    @RequestParam(required = false, defaultValue = "10") Integer tamanhoPagina,
                                                                    @RequestParam(required = false, defaultValue = "asc") String direcao,
                                                                    @RequestParam(required = false, defaultValue = "id") String ordenacao) throws Exception{
        return ResponseEntity.ok(PagamentoRestFactory.fromPageEntityToPageDTO(pagamentoService.listarPagamentos(codigoDebito, cpfPagador, cnpjPagador, statusPagamento, paginaAtual, tamanhoPagina, direcao, ordenacao))); 
   }

   @PutMapping("excluir-pagamento")
   public ResponseEntity<MessageResponseDTO> putMethodName(@RequestBody Long id) {
       return ResponseEntity.ok(PagamentoRestFactory.fromStringToJsonMessagem(pagamentoService.exclusaoLogica(id)));
   }
    

}

package com.br.api.restcontroller;

import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.service.FormaPagamentoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forma-pagamento")
public class FormaPagamentoRestController {

    private final FormaPagamentoService formaPagamentoService;

    FormaPagamentoRestController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @GetMapping
    public List<FormaPagamentoResponseDTO> findAll(){
        return formaPagamentoService.all();
    }
}

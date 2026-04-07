package com.br.api.restcontroller;

import com.br.dto.response.PessoaResponseDTO;
import com.br.service.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pessoa")
public class PessoaRestController {

    private final PessoaService pessoaService;

    public PessoaRestController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PessoaResponseDTO>> findPessoasList(
            @RequestParam(value = "busca", required = false) String busca,
            Sort sort){
        return new ResponseEntity<>(this.pessoaService.find(busca, sort), HttpStatus.OK);
    }

}
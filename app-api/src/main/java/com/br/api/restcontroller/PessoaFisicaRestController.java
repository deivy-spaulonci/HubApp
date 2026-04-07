package com.br.api.restcontroller;

import com.br.dto.request.create.PessoaFisicaRequestCreateDTO;
import com.br.dto.response.PessoaFisicaResponseDTO;
import com.br.service.PessoaFisicaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/pessoa-fisica")
public class PessoaFisicaRestController {
    private final PessoaFisicaService pessoaFisicaService;

    public PessoaFisicaRestController(PessoaFisicaService pessoaFisicaService) {
        this.pessoaFisicaService = pessoaFisicaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<PessoaFisicaResponseDTO> findByNomeOrCpfPaged(
            @RequestParam(value = "busca", required = false) String busca,
            Pageable pageable) {
        Pageable pageableComId = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by("id").ascending()) // Adiciona o ID no final
        );
        return pessoaFisicaService.findByNomeOrCpfPaged(busca, pageableComId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PessoaFisicaResponseDTO> create(
            @RequestBody @Valid PessoaFisicaRequestCreateDTO pessoaFisicaRequestCreateDTO){
        return new ResponseEntity<>(pessoaFisicaService.save(pessoaFisicaRequestCreateDTO), HttpStatus.CREATED);
    }
}

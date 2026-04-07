package com.br.api.restcontroller;

import com.br.dto.request.create.PessoaJuridicaRequestCreateDTO;
import com.br.dto.response.PessoaJuridicaResponseDTO;
import com.br.service.PessoaJuridicaService;
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
@RequestMapping("/api/v1/pessoa-juridica")
public class PessoaJuridicaRestController {

    private final PessoaJuridicaService pessoaJuridicaService;

    public PessoaJuridicaRestController(PessoaJuridicaService pessoaJuridicaService) {
        this.pessoaJuridicaService = pessoaJuridicaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<PessoaJuridicaResponseDTO> findByNomeOrCnpjOrRazaoPaged(
            @RequestParam(value = "busca", required = false) String busca,
            Pageable pageable) {
        Pageable pageableComId = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by("id").ascending()) // Adiciona o ID no final
        );
        return pessoaJuridicaService.findByNomeOrCnpjOrRazaoPaged(busca, pageableComId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PessoaJuridicaResponseDTO> create(@RequestBody @Valid PessoaJuridicaRequestCreateDTO pessoaJuridicaRequestCreateDTO) {
        return new ResponseEntity<>(pessoaJuridicaService.save(pessoaJuridicaRequestCreateDTO), HttpStatus.CREATED);
    }
}

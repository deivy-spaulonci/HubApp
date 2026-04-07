package com.br.api.restcontroller;

import com.br.dto.request.create.ContaRequestCreateDTO;
import com.br.dto.request.update.ContaRequestUpdateDTO;
import com.br.dto.response.ContaResponseDTO;
import com.br.dto.response.ContaResponseGastoTipoDTO;
import com.br.dto.response.ContaResponseTotaisStatusDTO;
import com.br.filter.ContaFilter;
import com.br.service.ContaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/conta")
public class ContaRestController {

    private final ContaService contaService;

    public ContaRestController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ContaResponseDTO> getContasPage(@ModelAttribute ContaFilter contaFilter,Pageable pageable) {
        Pageable pageableComId = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by("id").ascending()) // Adiciona o ID no final
        );
        return contaService.findContaPaged(contaFilter,pageableComId);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ContaResponseDTO> getContasList(@ModelAttribute ContaFilter contaFilter) {
        return contaService.findConta(contaFilter);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Long getCount() {
        return contaService.getCountConta();
    }

    @GetMapping("/valorTotal")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal get(@ModelAttribute ContaFilter contaFilter){
        return contaService.getSumConta(contaFilter);
    }

    @GetMapping("/totalPorTipo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ContaResponseGastoTipoDTO>> gastoTotalTipo(){
        return new ResponseEntity<List<ContaResponseGastoTipoDTO>>(contaService.gastoTotalTipo(), HttpStatus.OK);
    }

    @GetMapping("/totaisStatus")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ContaResponseTotaisStatusDTO>> totaisStatus(){
        return new ResponseEntity<List<ContaResponseTotaisStatusDTO>>(contaService.totaisStatus(), HttpStatus.OK);
    }

    @GetMapping("/uuid")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContaResponseDTO> gastoTotalMensal(@RequestParam(value = "uuid", required = true) UUID uuid){
        return new ResponseEntity<>(contaService.findByUuid(uuid), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid ContaRequestCreateDTO contaRequestCreateDTO){
        return new ResponseEntity<>(contaService.create(contaRequestCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody @Valid ContaRequestUpdateDTO contaRequestUpdateDTO){
        return new ResponseEntity<>(contaService.update(contaRequestUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("uuid") UUID uuid){
        contaService.deleteByUuid(uuid);
    }

}

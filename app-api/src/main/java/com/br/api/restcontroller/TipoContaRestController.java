package com.br.api.restcontroller;

import com.br.dto.request.create.TipoContaRequestCreateDTO;
import com.br.dto.request.update.TipoContaRequestUpdateDTO;
import com.br.dto.response.TipoContaResponseDTO;
import com.br.service.TipoContaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/tipo-conta")
public class TipoContaRestController {

    private final TipoContaService tipoContaService;

    public TipoContaRestController(TipoContaService tipoContaService) {
        this.tipoContaService = tipoContaService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TipoContaResponseDTO> findAll() {
        return tipoContaService.findAll();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody @Valid TipoContaRequestUpdateDTO tipoContaRequestUpdateDTO){
        return new ResponseEntity<>(tipoContaService.update(tipoContaRequestUpdateDTO), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody @Valid TipoContaRequestCreateDTO tipoContaRequestCreateDTO){
        return new ResponseEntity<>(tipoContaService.create(tipoContaRequestCreateDTO), HttpStatus.OK);
    }

}

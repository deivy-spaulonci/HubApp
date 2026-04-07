package com.br.api.restcontroller;

import com.br.dto.request.create.ParametroRequestCreateDTO;
import com.br.dto.response.ParametroResponseDTO;
import com.br.service.ParametroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parametro")
public class ParametroRestController {
    private final ParametroService parametroService;

    public ParametroRestController(ParametroService parametroService) {
        this.parametroService = parametroService;
    }

    @GetMapping
    public List<ParametroResponseDTO> findAll(){
        return parametroService.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody @Valid ParametroRequestCreateDTO  parametroRequestCreateDTO){
        return new ResponseEntity<>(parametroService.save(parametroRequestCreateDTO), HttpStatus.OK);
    }

}

package com.br.api.restcontroller;

import com.br.service.ArquivoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/arquivo")
public class ArquivoRestController {

    private final ArquivoService  arquivoService;

    public ArquivoRestController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @PutMapping("/renomear")
    public ResponseEntity<String> renomear(
            @RequestParam("pathCompleto") String pathCompleto,
            @RequestParam("novoNome") String novoNome) {
        try {
            arquivoService.renomearArquivo(pathCompleto, novoNome);
            return ResponseEntity.ok("Arquivo renomeado com sucesso!");
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao renomear arquivo.");
        }
    }
}

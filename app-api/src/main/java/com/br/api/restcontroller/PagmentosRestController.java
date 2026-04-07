package com.br.api.restcontroller;

import com.br.dto.response.TreeNodeResponseDTO;
import com.br.service.PagamentosArquivoService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagamentos")
public class PagmentosRestController {

    private final PagamentosArquivoService pagamentosArquivoService;

    public PagmentosRestController(PagamentosArquivoService pagamentosArquivoService) {
        this.pagamentosArquivoService = pagamentosArquivoService;
    }

    @GetMapping("/pasta")
    public List<TreeNodeResponseDTO> pagamentoFiles(){
        return pagamentosArquivoService.listarEstruturaDiretorio();
    }

    @GetMapping("/visualizar")
    public ResponseEntity<?> visualizarArquivo(@RequestParam("pathCompleto") String pathCompleto) {
        try {
            Resource arquivo = pagamentosArquivoService.carregarArquivo(pathCompleto);
            String contentType = pagamentosArquivoService.identificarTipoArquivo(pathCompleto);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(arquivo);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.br.api.restcontroller;

import com.br.dto.request.create.DespesaRequestCreateDTO;
import com.br.dto.request.update.DespesaRequestUpdateDTO;
import com.br.dto.response.DespesaResponseDTO;
import com.br.dto.response.DespesaResponseGastoAnualDTO;
import com.br.dto.response.DespesaResponseGastoMensalDTO;
import com.br.dto.response.DespesaResponseGastoTipoDTO;
import com.br.filter.DespesaFilter;
import com.br.service.DespesaService;
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
@RequestMapping("/api/v1/despesa")
public class DespesaRestController {

    private final DespesaService despesaService;

    public DespesaRestController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<DespesaResponseDTO> findAll(@ModelAttribute DespesaFilter despesaFilter, Pageable pageable){
        /**
         * Se você usa ORDER BY em uma coluna que possui valores repetidos (como data_pagamento ou status), o PostgreSQL
         * não garante a ordem interna desses registros iguais.
         *
         * O que acontece: Na página 1, o registro "X" aparece. Quando você vai para a página 2, o banco muda a ordem
         * interna desses valores repetidos e o registro "X" acaba sendo "pulado" ou duplicado.
         * A Solução: Adicione sempre a chave primária (id) ao final do seu ORDER BY para garantir uma ordem única e imutável.
         */
        Pageable pageableComId = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by("id").ascending()) // Adiciona o ID no final
        );
        Page<DespesaResponseDTO> page = despesaService.findDespesaPaged(despesaFilter, pageableComId);
        return page;
    }

    @GetMapping("/valorTotal")
    public BigDecimal get(@ModelAttribute DespesaFilter despesaFilter){
        return despesaService.totalDespesa(despesaFilter);
    }

    @GetMapping("/totalPorAno")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DespesaResponseGastoAnualDTO>> gastoTotalAnual(){
        return new ResponseEntity<List<DespesaResponseGastoAnualDTO>>(despesaService.gastoTotalAnual(), HttpStatus.OK);
    }

    @GetMapping("/totalPorMes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DespesaResponseGastoMensalDTO>> gastoTotalMensal(@RequestParam(value = "ano", required = true) Integer ano){
        return new ResponseEntity<List<DespesaResponseGastoMensalDTO>>(despesaService.gastoTotalMensal(ano), HttpStatus.OK);
    }

    @GetMapping("/totalPorTipo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DespesaResponseGastoTipoDTO>> gastoTotalTipo(){
        return new ResponseEntity<List<DespesaResponseGastoTipoDTO>>(despesaService.gastoTotalTipo(), HttpStatus.OK);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Long getCount() {
        return despesaService.getCountDespesa();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid DespesaRequestCreateDTO despesaRequestCreateDTO){
        return new ResponseEntity<>(despesaService.create(despesaRequestCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DespesaResponseDTO> update(@RequestBody @Valid DespesaRequestUpdateDTO despesaRequestUpdateDTO){
        return new ResponseEntity<>(despesaService.update(despesaRequestUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> delete(@RequestParam UUID uuid){
        try{
            despesaService.delete(uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping(value = "/lote", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<?> createDespesaPorLote(@RequestParam("file") MultipartFile file){
//        try {
//            List<DespesaRequestCreateLoteDTO> novos = new ArrayList<>();
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
//                // Pular o cabeçalho (ID, Produto, Preco)
//                String linha = reader.readLine();
//                while ((linha = reader.readLine()) != null) {
//                    String[] colunas = linha.split(",");
//                    DespesaRequestCreateLoteDTO despesaRequestCreateLoteDTO  = DespesaRequestCreateLoteDTO.builder()
//                            .cnpj(colunas[0])
//                            .dataPagamento(colunas[1])
//                            .valor(new BigDecimal(colunas[2]))
//                            .tipoDespesa(colunas[3])
//                            .idFormaPagamento(new BigInteger(colunas[4]))
//                            .observacao(colunas.length == 6 ? colunas[5] : "")
//                            .build();
//                    novos.add(despesaRequestCreateLoteDTO);
//                }
//                despesaService.saveLote(novos);
//            }
//            return ResponseEntity.ok("Processados " + novos.size() + " registros/despesas.");
//        } catch (Exception e) {
//            return ResponseEntity.
//                    status(HttpStatus.BAD_REQUEST)
//                    .body("Erro ao carregar CSV: " + e.getMessage());
//        }
//    }
//
//    @PostMapping(value = "/checkLote", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<?> checkLote(@RequestParam("file") MultipartFile file){
//        try {
//            List<DespesaRequestCreateLoteDTO> novos = new ArrayList<>();
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
//                String linha = reader.readLine();
//                while ((linha = reader.readLine()) != null) {
//                    String[] colunas = linha.split(",");
//                    DespesaRequestCreateLoteDTO despesaRequestCreateLoteDTO  = DespesaRequestCreateLoteDTO.builder()
//                            .cnpj(colunas[0])
//                            .dataPagamento(colunas[1])
//                            .valor(new BigDecimal(colunas[2]))
//                            .tipoDespesa(colunas[3])
//                            .idFormaPagamento(new BigInteger(colunas[4]))
//                            .observacao(colunas.length != 6 ? "" : colunas[5].toString())
//                            .build();
//                    novos.add(despesaRequestCreateLoteDTO);
//                }
//                despesaService.checkLote(novos);
//            }
//            return ResponseEntity.ok("Validados " + novos.size() + " registros/despesas.");
//        } catch (Exception e) {
//            return ResponseEntity.
//                    status(HttpStatus.BAD_REQUEST)
//                    .body("Erro ao carregar CSV: " + e.getMessage());
//        }
//    }
}

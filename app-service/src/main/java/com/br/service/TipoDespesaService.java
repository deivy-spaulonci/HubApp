package com.br.service;

import com.br.dto.response.TipoDespesaResponseDTO;
import com.br.entity.TipoDespesaEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TipoDespesaService {

    public List<TipoDespesaResponseDTO> all() {
        return Arrays.stream(TipoDespesaEnum.values())
                .map(e -> new TipoDespesaResponseDTO(
                        e.getValue(),
                        e.getNome()
                ))
                .toList();
    }
}
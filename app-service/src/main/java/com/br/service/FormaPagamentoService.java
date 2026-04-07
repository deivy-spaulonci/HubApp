package com.br.service;

import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.entity.FormaPagamento;
import com.br.mapper.FormaPagamentoMapper;
import com.br.repository.FormaPagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;
    private static final FormaPagamentoMapper formaPagamentoMapper = FormaPagamentoMapper.INSTANCE;

    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public List<FormaPagamentoResponseDTO> all() {
        return formaPagamentoMapper.toListPagamentoResponseDTO(formaPagamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "modalidade")));
    }

    public FormaPagamento findByUuid(UUID uuid) {
        return formaPagamentoRepository.findByUuid(uuid).orElseThrow(() -> new
                EntityNotFoundException("Forma Pagamento não encontrada com o uudi: " + uuid));
    }
}

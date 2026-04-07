package com.br.service;

import com.br.dto.request.create.TipoContaRequestCreateDTO;
import com.br.dto.request.update.TipoContaRequestUpdateDTO;
import com.br.dto.response.TipoContaResponseDTO;
import com.br.entity.TipoConta;
import com.br.mapper.TipoContaMapper;
import com.br.repository.TipoContaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TipoContaService {
    private final TipoContaRepository tipoContaRepository;
    private static final TipoContaMapper tipoContaMapper = TipoContaMapper.INSTANCE;

    public TipoContaService(TipoContaRepository tipoContaRepository) {
        this.tipoContaRepository = tipoContaRepository;
    }

    public List<TipoContaResponseDTO> findAll(){
        return tipoContaMapper.toListTipoContaResponseDTO(tipoContaRepository.findAll(Sort.by("nome")));
    }

    public TipoContaResponseDTO update(TipoContaRequestUpdateDTO tipoContaRequestUpdateDTO){
        TipoConta tipoContaBase = this.findByUuid(tipoContaRequestUpdateDTO.uuid());
        TipoConta tipoContaUpdate = tipoContaMapper.toEntity(tipoContaRequestUpdateDTO);
        tipoContaBase.setNome(tipoContaUpdate.getNome());
        tipoContaBase.setCartaoCredito(tipoContaUpdate.getCartaoCredito());
        tipoContaBase.setAtivo(tipoContaUpdate.getAtivo());
        return tipoContaMapper.toTipoContaResponseDTO(tipoContaRepository.save(tipoContaBase));
    }

    public TipoContaResponseDTO create(TipoContaRequestCreateDTO tipoContaRequestCreateDTO){
        TipoConta tipoContaNew = tipoContaMapper.toEntity(tipoContaRequestCreateDTO);
        return tipoContaMapper.toTipoContaResponseDTO(tipoContaRepository.save(tipoContaNew));
    }

    public TipoConta findByUuid(UUID uuid){
        return Optional.of(tipoContaRepository.findTipoContaByUuid(uuid))
                .orElseThrow(() -> new EntityNotFoundException("Tipo de conta não encontrado com o uudi: " + uuid));
    }
}

package com.br.service;

import com.br.dto.request.create.PessoaFisicaRequestCreateDTO;
import com.br.dto.request.update.PessoaFisicaRequestUpdateDTO;
import com.br.dto.response.PessoaFisicaResponseDTO;
import com.br.entity.PessoaFisica;
import com.br.mapper.PessoaFisicaMapper;
import com.br.repository.PessoaFisicaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaFisicaService {

    private final PessoaFisicaRepository pessoaFisicaRepository;
    private static final PessoaFisicaMapper pessoaFisicaMapper = PessoaFisicaMapper.INSTANCE;

    public PessoaFisicaService(PessoaFisicaRepository pessoaFisicaRepository) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    public Page<PessoaFisicaResponseDTO> findByNomeOrCpfPaged(String busca, Pageable pageable) {
        return this.pessoaFisicaRepository.findByNomeCpfPaged(busca,pageable);
    }

    public List<PessoaFisicaResponseDTO> findByNomeOrCpf(String busca){
        return this.pessoaFisicaRepository.findByNomeCpf(busca);
    }

    public PessoaFisicaResponseDTO getPessoaByUuid(UUID uuid){
        PessoaFisica pessoaFisica = findByUuid(uuid);
        return pessoaFisicaMapper.toResponseDTO(pessoaFisica);
    }

    public PessoaFisica findByUuid(UUID uuid){
        return Optional.of(this.pessoaFisicaRepository.findPessoaFisicaByUuid(uuid))
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrado com o uudi: " + uuid));
    }

    @Transactional
    public PessoaFisicaResponseDTO save(PessoaFisicaRequestCreateDTO pessoaFisicaRequestCreateDTO){
        PessoaFisica pessoaFisicaNew = pessoaFisicaMapper.toEntity(pessoaFisicaRequestCreateDTO);
        pessoaFisicaNew.setTipo('F');
        pessoaFisicaNew.setFornecedor(true);
        PessoaFisica pessoaFisicaSaved = pessoaFisicaRepository.save(pessoaFisicaNew);
        return pessoaFisicaMapper.toResponseDTO(pessoaFisicaSaved);
    }

    @Transactional
    public PessoaFisicaResponseDTO update(PessoaFisicaRequestUpdateDTO pessoaFisicaRequestUpdateDTO){
        PessoaFisica pessoaFisicaNew = pessoaFisicaMapper.toEntity(pessoaFisicaRequestUpdateDTO);
        pessoaFisicaNew.setTipo('F');
        pessoaFisicaNew.setFornecedor(pessoaFisicaRequestUpdateDTO.uuid() == null || pessoaFisicaRequestUpdateDTO.fornecedor());
        PessoaFisica pessoaFisicaSaved = pessoaFisicaRepository.save(pessoaFisicaNew);
        return pessoaFisicaMapper.toResponseDTO(pessoaFisicaSaved);
    }
}

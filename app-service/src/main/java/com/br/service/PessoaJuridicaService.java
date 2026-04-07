package com.br.service;

import com.br.dto.request.create.PessoaJuridicaRequestCreateDTO;
import com.br.dto.request.update.PessoaJuridicaRequestUpdateDTO;
import com.br.dto.response.PessoaJuridicaResponseDTO;
import com.br.entity.PessoaJuridica;
import com.br.mapper.PessoaJuridicaMapper;
import com.br.repository.PessoaJuridicaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaJuridicaService {

    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    private static final PessoaJuridicaMapper pessoaJuridicaMapper = PessoaJuridicaMapper.INSTANCE;
    private final String RECEITA_CLOUD = "https://publica.cnpj.ws/cnpj/";

    public PessoaJuridicaService(PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }

    public Page<PessoaJuridicaResponseDTO> findByNomeOrCnpjOrRazaoPaged(String busca, Pageable pageable){
        return pessoaJuridicaRepository.findByNomeOrRazaoSocialOrCnpjPage(busca,pageable);
    }

    public List<PessoaJuridicaResponseDTO> findByNomeOrCnpjOrRazaoPaged(String busca){
        return pessoaJuridicaRepository.findByNomeOrRazaoSocialOrCnpj(busca);
    }

    @Transactional
    public PessoaJuridicaResponseDTO save(PessoaJuridicaRequestCreateDTO pessoaJuridicaRequestCreateDTO){
        PessoaJuridica pessoaJuridicaNew = pessoaJuridicaMapper.toEntity(pessoaJuridicaRequestCreateDTO);
        pessoaJuridicaNew.setTipo('J');
        pessoaJuridicaNew.setFornecedor(true);
        PessoaJuridica pessoaJuridicaSaved = pessoaJuridicaRepository.save(pessoaJuridicaNew);
        return pessoaJuridicaMapper.toResponseDTO(pessoaJuridicaSaved);
    }

    @Transactional
    public PessoaJuridicaResponseDTO update(PessoaJuridicaRequestUpdateDTO pessoaJuridicaRequestUpdateDTO){
        PessoaJuridica pessoaJuridicaNew = pessoaJuridicaMapper.toEntity(pessoaJuridicaRequestUpdateDTO);
        pessoaJuridicaNew.setTipo('J');
        pessoaJuridicaNew.setFornecedor(true);
        PessoaJuridica pessoaJuridicaSaved = pessoaJuridicaRepository.save(pessoaJuridicaNew);
        return pessoaJuridicaMapper.toResponseDTO(pessoaJuridicaSaved);
    }

    public Optional<PessoaJuridica> findByCNPJ(String cnpj){
        return pessoaJuridicaRepository.findByCnpj(cnpj);
    }
}

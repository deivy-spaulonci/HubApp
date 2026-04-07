package com.br.service;

import com.br.dto.request.create.ParametroRequestCreateDTO;
import com.br.dto.response.ParametroResponseDTO;
import com.br.entity.Parametro;
import com.br.mapper.ParametroMapper;
import com.br.repository.ParametoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParametroService {
    private final ParametoRepository parametrosRepository;
    private final ParametroMapper parametroMapper = ParametroMapper.INSTANCE;

    public ParametroService(ParametoRepository parametrosRepository) {
        this.parametrosRepository = parametrosRepository;
    }

    public List<ParametroResponseDTO> all(){
        return parametroMapper.toListResponseDTO(parametrosRepository.findAll());
    }

    public Parametro findByChave(String chave){
        return parametrosRepository.findByChave(chave).orElseThrow(() ->
                new EntityNotFoundException("Parametro não econtrado com essa chave: " + chave));
    }

    public ParametroResponseDTO save(ParametroRequestCreateDTO parametroRequestCreateDTO){
        return parametroMapper.toDTO(parametrosRepository.save(parametroMapper.toEntity(parametroRequestCreateDTO)));
    }

    public ParametroResponseDTO update(String chave, String valor){
        Parametro parametro = findByChave(chave);
        parametro.setValor(valor);
        return parametroMapper.toDTO(parametrosRepository.save(parametro));
    }
}

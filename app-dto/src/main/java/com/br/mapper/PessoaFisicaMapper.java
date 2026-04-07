package com.br.mapper;

import com.br.dto.request.create.PessoaFisicaRequestCreateDTO;
import com.br.dto.request.update.PessoaFisicaRequestUpdateDTO;
import com.br.dto.response.PessoaFisicaResponseDTO;
import com.br.entity.PessoaFisica;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PessoaFisicaMapper {
    PessoaFisicaMapper INSTANCE = Mappers.getMapper(PessoaFisicaMapper.class);

    PessoaFisica toEntity(PessoaFisicaRequestCreateDTO pessoaFisicaRequestCreateDTO);

    PessoaFisica toEntity(PessoaFisicaRequestUpdateDTO pessoaFisicaRequestUpdateDTO);

    PessoaFisicaResponseDTO toResponseDTO(PessoaFisica pessoaFisica);

//    PessoaFisica parcialUpdate(PessoaFisicaRequestUpdateDTO pessoaFisicaRequestUpdateDTO);

    List<PessoaFisicaResponseDTO> toListResponseDTO(List<PessoaFisica> pessoaFisicas);

}

package com.br.mapper;

import com.br.dto.request.create.PessoaJuridicaRequestCreateDTO;
import com.br.dto.request.update.PessoaJuridicaRequestUpdateDTO;
import com.br.dto.response.PessoaJuridicaResponseDTO;
import com.br.entity.PessoaJuridica;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PessoaJuridicaMapper {
    PessoaJuridicaMapper INSTANCE = Mappers.getMapper(PessoaJuridicaMapper.class);

    PessoaJuridica toEntity(PessoaJuridicaRequestCreateDTO pessoaRequestCreateDTO);

    PessoaJuridica toEntity(PessoaJuridicaRequestUpdateDTO pessoaJuridicaRequestUpdateDTO);

    PessoaJuridicaResponseDTO toResponseDTO(PessoaJuridica pessoaJuridica);

//    PessoaJuridica partialUpdate(PessoaJuridicaRequestUpdateDTO pessoaJuridicaRequestUpdateDTO);

    List<PessoaJuridicaResponseDTO> toListResponseDTO(List<PessoaJuridica> pessoaJuridica);
}

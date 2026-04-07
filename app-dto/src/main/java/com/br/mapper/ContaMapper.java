package com.br.mapper;

import com.br.dto.request.create.ContaRequestCreateDTO;
import com.br.dto.request.update.ContaRequestUpdateDTO;
import com.br.dto.response.ContaResponseDTO;
import com.br.entity.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContaMapper {
    ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);

    List<ContaResponseDTO> toListContaResponseDTO(List<Conta> contas);

    ContaResponseDTO toContaResponseDTO(Conta conta);

    Conta toEntity(ContaRequestCreateDTO contaRequestCreateDTO);

    Conta toEntity(ContaRequestUpdateDTO contaRequestUpdateDTO);
}

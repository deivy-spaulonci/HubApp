package com.br.mapper;

import com.br.dto.request.create.ParametroRequestCreateDTO;
import com.br.dto.response.ParametroResponseDTO;
import com.br.entity.Parametro;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ParametroMapper {
    ParametroMapper INSTANCE = Mappers.getMapper(ParametroMapper.class);

    List<ParametroResponseDTO> toListResponseDTO(List<Parametro> Parametro);

    Parametro toEntity(ParametroRequestCreateDTO parametroRequestCreateDTO);

    ParametroResponseDTO toDTO(Parametro Parametro);

}

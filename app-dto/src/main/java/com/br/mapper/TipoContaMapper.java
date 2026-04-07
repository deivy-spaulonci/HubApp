package com.br.mapper;

import com.br.dto.request.create.TipoContaRequestCreateDTO;
import com.br.dto.request.update.TipoContaRequestUpdateDTO;
import com.br.dto.response.TipoContaResponseDTO;
import com.br.entity.TipoConta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoContaMapper {
    TipoContaMapper INSTANCE = Mappers.getMapper(TipoContaMapper.class);

    List<TipoContaResponseDTO>  toListTipoContaResponseDTO(List<TipoConta> tiposConta);

    TipoContaResponseDTO toTipoContaResponseDTO(TipoConta tipoConta);

    TipoConta toEntity(TipoContaRequestUpdateDTO tipoContaRequestUpdateDTO);

    TipoConta toEntity(TipoContaRequestCreateDTO tipoContaRequestCreateDTO);
}

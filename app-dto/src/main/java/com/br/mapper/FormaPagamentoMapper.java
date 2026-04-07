package com.br.mapper;

import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.entity.FormaPagamento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FormaPagamentoMapper {
    FormaPagamentoMapper INSTANCE = Mappers.getMapper(FormaPagamentoMapper.class);

    List<FormaPagamentoResponseDTO> toListPagamentoResponseDTO(List<FormaPagamento> formasPagamento);
}

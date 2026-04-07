package com.br.mapper;

import com.br.dto.request.create.DespesaRequestCreateDTO;
import com.br.dto.request.ref.TipoDespesaRefDTO;
import com.br.dto.request.update.DespesaRequestUpdateDTO;
import com.br.dto.response.DespesaResponseDTO;
import com.br.entity.Despesa;
import com.br.entity.TipoDespesaEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DespesaMapper {
    DespesaMapper INSTANCE = Mappers.getMapper(DespesaMapper.class);

    List<DespesaResponseDTO> toListDespesaResponseDTO(List<Despesa> despesas);

    //@Mapping(source = "fornecedor.pessoaJuridica.razaoSocial", target = "fornecedor.razaoSocial")
    DespesaResponseDTO toDespesaResponseDTO(Despesa despesa);

    Despesa toEntity(DespesaRequestCreateDTO despesaRequestCreateDTO);

    Despesa toEntity(DespesaRequestUpdateDTO despesaRequestUpdateDTO);

//    @Mapping(source = "dataPagamento", target = "dataPagamento", dateFormat = "ddMMyyyy")
//    @Mapping(source = "idFormaPagamento", target = "formaPagamento.id")
//    Despesa toEntity(DespesaRequestCreateLoteDTO despesaRequestCreateLoteDTO);

    default TipoDespesaEnum map(TipoDespesaRefDTO value) {
        return TipoDespesaEnum.forValues(value.value());
    }

}

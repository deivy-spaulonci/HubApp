package com.br.service;

import com.br.dto.request.create.ContaRequestCreateDTO;
import com.br.dto.request.update.ContaRequestUpdateDTO;
import com.br.dto.response.ContaResponseDTO;
import com.br.dto.response.ContaResponseGastoTipoDTO;
import com.br.dto.response.ContaResponseTotaisStatusDTO;
import com.br.entity.Conta;
import com.br.filter.ContaFilter;
import com.br.mapper.ContaMapper;
import com.br.repository.ContaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ContaService {
    private final ContaRepository contaRepository;
    private static final ContaMapper contaMapper = ContaMapper.INSTANCE;
    private final TipoContaService tipoContaService;
    private final FormaPagamentoService formaPagamentoService;

    @PersistenceContext
    private EntityManager em;

    public ContaService(ContaRepository contaRepository,
                        FormaPagamentoService formaPagamentoService,
                        TipoContaService tipoContaService) {
        this.tipoContaService = tipoContaService;
        this.contaRepository = contaRepository;
        this.formaPagamentoService = formaPagamentoService;
    }

    public Page<ContaResponseDTO> findContaPaged(ContaFilter contaFilter, Pageable pageable) {
        Page<Conta> contaPage = contaRepository.listContaPaged(pageable,  contaFilter);
        return contaPage.map(contaMapper::toContaResponseDTO);
    }

    public List<ContaResponseDTO> findConta(ContaFilter contaFilter) {
        List<Conta> contaList = contaRepository.listConta(contaFilter);
        return contaMapper.toListContaResponseDTO(contaList);
    }

    public BigDecimal getSumConta(ContaFilter contaFilter) {
        return contaRepository.getSumConta(contaFilter, em);
    }

    public long getCountConta() {
        return contaRepository.count();
    }

    @Transactional
    public ContaResponseDTO create(ContaRequestCreateDTO contaRequestCreateDTO) {
        Conta contaNew = contaMapper.toEntity(contaRequestCreateDTO);
        contaNew.setDataLancamento(LocalDateTime.now());
        contaNew.setTipoConta(tipoContaService.findByUuid(contaNew.getTipoConta().getUuid()));
        if(Objects.nonNull(contaNew.getFormaPagamento()))
            contaNew.setFormaPagamento(formaPagamentoService.findByUuid(contaNew.getFormaPagamento().getUuid()));

        return contaMapper.toContaResponseDTO(contaRepository.save(contaNew));
    }

    @Transactional
    public ContaResponseDTO update(ContaRequestUpdateDTO contaRequestUpdateDTO){
        Conta contaUpdated = contaMapper.toEntity(contaRequestUpdateDTO);
        Conta contaDataBase = contaRepository.findByUuid(contaUpdated.getUuid()).get();
        if(contaDataBase.getDataLancamento()==null)
            contaDataBase.setDataLancamento(LocalDateTime.now());
        contaDataBase.setComprovante(contaUpdated.getComprovante());
        contaDataBase.setEmissao(contaUpdated.getEmissao());
        contaDataBase.setVencimento(contaUpdated.getVencimento());
        contaDataBase.setValor(contaUpdated.getValor());
        contaDataBase.setParcela(contaUpdated.getParcela());
        contaDataBase.setTotalParcela(contaUpdated.getTotalParcela());
        contaDataBase.setCodigoBarras(contaUpdated.getCodigoBarras());
        contaDataBase.setObservacao(contaUpdated.getObservacao());
        contaDataBase.setDataPagamento(contaUpdated.getDataPagamento());
        contaDataBase.setTitulo(contaUpdated.getTitulo());
        contaDataBase.setComprovante(contaUpdated.getComprovante());
        contaDataBase.setValorPago(contaUpdated.getValorPago());
        if(contaDataBase.getFormaPagamento() == null || !contaDataBase.getFormaPagamento().getUuid().equals(contaUpdated.getFormaPagamento().getUuid()))
            if(contaUpdated.getFormaPagamento() != null)
                contaDataBase.setFormaPagamento(formaPagamentoService.findByUuid(contaUpdated.getFormaPagamento().getUuid()));
        if(!contaDataBase.getTipoConta().getUuid().equals(contaUpdated.getTipoConta().getUuid()))
            contaDataBase.setTipoConta(tipoContaService.findByUuid(contaUpdated.getTipoConta().getUuid()));
        return contaMapper.toContaResponseDTO(contaRepository.save(contaDataBase));
    }

    @Transactional
    public void deleteByUuid(UUID uuid) {
        Conta conta = contaRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Conta não encontrado com o uudi: " + uuid));
        if (Objects.nonNull(conta))
            contaRepository.deleteById(conta.getId());
    }

    public ContaResponseDTO findByUuid(UUID uuid){
        Conta conta = contaRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Conta não encontrado com o uudi: " + uuid));
        return contaMapper.toContaResponseDTO(conta);
    }

    public List<ContaResponseGastoTipoDTO> gastoTotalTipo(){
        return contaRepository.gastoTotalTipo();
    }

    public List<ContaResponseTotaisStatusDTO> totaisStatus(){
        return contaRepository.totaisStatus();
    }
}

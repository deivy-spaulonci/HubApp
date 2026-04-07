package com.br.service;

import com.br.dto.request.create.DespesaRequestCreateDTO;
import com.br.dto.request.update.DespesaRequestUpdateDTO;
import com.br.dto.response.DespesaResponseDTO;
import com.br.dto.response.DespesaResponseGastoAnualDTO;
import com.br.dto.response.DespesaResponseGastoMensalDTO;
import com.br.dto.response.DespesaResponseGastoTipoDTO;
import com.br.entity.Despesa;
import com.br.filter.DespesaFilter;
import com.br.mapper.DespesaMapper;
import com.br.repository.DespesaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class DespesaService {
    @PersistenceContext
    private EntityManager em;

    private final DespesaRepository despesaRepository;
    private final PessoaService pessoaService;
    private final FormaPagamentoService formaPagamentoService;

    private static final DespesaMapper despesaMapper = DespesaMapper.INSTANCE;

    public DespesaService(DespesaRepository despesaRepository,
                          PessoaService pessoaService,
                          FormaPagamentoService formaPagamentoService) {
        this.despesaRepository = despesaRepository;
        this.pessoaService = pessoaService;
        this.formaPagamentoService = formaPagamentoService;
    }

    public Page<DespesaResponseDTO> findDespesaPaged(DespesaFilter despesaFilter, Pageable pageable) {
        Page<Despesa> despesaPage = despesaRepository.listDespesaPaged(pageable, despesaFilter);
        return despesaPage.map(despesaMapper::toDespesaResponseDTO);
    }

    public List<DespesaResponseDTO> all(DespesaFilter despesaFilter){
        List<Despesa> despesas = despesaRepository.all(despesaFilter);
        return despesaMapper.toListDespesaResponseDTO(despesas);
    }

    public BigDecimal totalDespesa(DespesaFilter despesaFilter) {
        return despesaRepository.getSumTotalDespesa(despesaFilter, em);
    }

    public DespesaResponseDTO getDespesaByUuid(UUID uuid){
        Despesa despesa = findByUuid(uuid);
        return  despesaMapper.toDespesaResponseDTO(despesa);
    }

    public Despesa findByUuid(UUID uuid){
        return Optional.of(despesaRepository.findByUuid(uuid))
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrado com o uudi: " + uuid));
    }

    public List<DespesaResponseGastoAnualDTO> gastoTotalAnual(){
        return despesaRepository.gastoTotalAnual();
    }

    public List<DespesaResponseGastoMensalDTO> gastoTotalMensal(Integer ano){
        return despesaRepository.gastoTotalMensal(ano);
    }

    @Transactional
    public void delete(UUID uuid){
        despesaRepository.delete(findByUuid(uuid));
    }

    public List<DespesaResponseGastoTipoDTO> gastoTotalTipo(){
        return despesaRepository.gastoTotalTipo();
    }

    @Transactional
    public DespesaResponseDTO update(DespesaRequestUpdateDTO despesaRequestUpdateDTO){
        Despesa despesaUpdated = despesaMapper.toEntity(despesaRequestUpdateDTO);
        Despesa despesaDataBase = despesaRepository.findByUuid(despesaUpdated.getUuid());
        despesaDataBase.setTipoDespesa(despesaUpdated.getTipoDespesa());
        despesaDataBase.setValor(despesaUpdated.getValor());
        despesaDataBase.setDataPagamento(despesaUpdated.getDataPagamento());
        despesaDataBase.setObservacao(despesaUpdated.getObservacao());
        if(!despesaDataBase.getFornecedor().getUuid().equals(despesaUpdated.getFornecedor().getUuid()))
            despesaDataBase.setFornecedor(pessoaService.findPessoaByUuid(despesaUpdated.getFornecedor().getUuid()));
        if(!despesaDataBase.getFormaPagamento().getUuid().equals(despesaUpdated.getFormaPagamento().getUuid()))
            despesaDataBase.setFormaPagamento(formaPagamentoService.findByUuid(despesaUpdated.getFormaPagamento().getUuid()));
        return despesaMapper.toDespesaResponseDTO(despesaRepository.save(despesaDataBase));
    }

    @Transactional
    public DespesaResponseDTO create(DespesaRequestCreateDTO despesaRequestCreateDTO){
        Despesa despesaNew = despesaMapper.toEntity(despesaRequestCreateDTO);
        despesaNew.setFornecedor(pessoaService.findPessoaByUuid(despesaNew.getFornecedor().getUuid()));
        despesaNew.setFormaPagamento(formaPagamentoService.findByUuid(despesaNew.getFormaPagamento().getUuid()));
        despesaNew.setDataLancamento(LocalDateTime.now());
        return despesaMapper.toDespesaResponseDTO(despesaRepository.save(despesaNew));
    }

    public long getCountDespesa() {
        return despesaRepository.count();
    }

    /**
     * parte de importacao de lote
     * @param listDespesaRequestCreateLoteDTO
     */
//    public void checkLote(List<DespesaRequestCreateLoteDTO> listDespesaRequestCreateLoteDTO){
//        listDespesaRequestCreateLoteDTO.stream().forEach(despesaRequestCreateLoteDTO -> {
//            validarDTO(despesaRequestCreateLoteDTO);
//            validaIds(despesaRequestCreateLoteDTO);
//            Pessoa pessoa = validaCNPJ(despesaRequestCreateLoteDTO);
//        });
//    }
//
//    public void saveLote(List<DespesaRequestCreateLoteDTO> listDespesaRequestCreateLoteDTO){
//        List<Despesa> novos = new ArrayList<>();
//        listDespesaRequestCreateLoteDTO.stream().forEach(despesaRequestCreateLoteDTO -> {
//            validarDTO(despesaRequestCreateLoteDTO);
//            validaIds(despesaRequestCreateLoteDTO);
//            Pessoa pessoa = validaCNPJ(despesaRequestCreateLoteDTO);
//            Despesa despesa = despesaMapper.toEntity(despesaRequestCreateLoteDTO);
//            despesa.setFornecedor(pessoa);
//            despesa.setDataLancamento(LocalDateTime.now());
//            novos.add(despesa);
//        });
//        despesaRepository.saveAll(novos);
//    }
//
//    public void validaIds(DespesaRequestCreateLoteDTO despesaRequestCreateLoteDTO){
//        if(!formaPagamentoService.findById(despesaRequestCreateLoteDTO.getIdFormaPagamento()).isPresent()){
//            throw new RuntimeException("o Id tipo de forma de pagamento esta inválidos!" + " *** " + despesaRequestCreateLoteDTO + " *** ");
//        }
//    }
//
//    public void validarDTO(DespesaRequestCreateLoteDTO despesaRequestCreateLoteDTO) {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<DespesaRequestCreateLoteDTO>> violations = validator.validate(despesaRequestCreateLoteDTO);
//        if (!violations.isEmpty()) {
//            for (ConstraintViolation<DespesaRequestCreateLoteDTO> violation : violations) {
//                log.error("Erro: " + violation.getMessage() + " *** " + despesaRequestCreateLoteDTO + " *** ");
//            }
//            throw new RuntimeException("Dados do DTO inválidos!");
//        }
//        log.info("DTO válido!");
//    }
//
//    public Pessoa validaCNPJ(DespesaRequestCreateLoteDTO despesaRequestCreateLoteDTO) {
//        Optional<PessoaJuridica> optionalPessoaJuridica = pessoaJuridicaService.findByCNPJ(despesaRequestCreateLoteDTO.getCnpj());
//        if(optionalPessoaJuridica.isPresent())
//            return optionalPessoaJuridica.get();
//        else
//            throw new RuntimeException("o CNPJ não esta cadastrado!" + " *** " + despesaRequestCreateLoteDTO + " *** ");
//    }
}

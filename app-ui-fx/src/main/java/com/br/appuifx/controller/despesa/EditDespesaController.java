package com.br.appuifx.controller.despesa;

import com.br.appuifx.factory.CreateField;
import com.br.appuifx.util.Alerts;
import com.br.appuifx.util.FormatUtil;
import com.br.appuifx.util.Validator;
import com.br.appuifx.view.shared.AutoComplete;
import com.br.appuifx.view.shared.CbDefault;
import com.br.dto.request.ref.FormaPagamentoRefDTO;
import com.br.dto.request.ref.PessoaRefDTO;
import com.br.dto.request.ref.TipoDespesaRefDTO;
import com.br.dto.request.update.DespesaRequestUpdateDTO;
import com.br.dto.response.DespesaResponseDTO;
import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.dto.response.PessoaResponseDTO;
import com.br.dto.response.TipoDespesaResponseDTO;
import com.br.service.DespesaService;
import com.br.service.FormaPagamentoService;
import com.br.service.PessoaService;
import com.br.service.TipoDespesaService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EditDespesaController {
    private DespesaService despesaService;
    private TipoDespesaService tipoDespesaService;
    private FormaPagamentoService formaPagamentoService;
    private PessoaService pessoaService;
    private PessoaResponseDTO pessoaSelec;
    /*======== FORM ========*/
    @FXML private CbDefault<TipoDespesaResponseDTO> cbTipoDespesa;
    @FXML private CbDefault<FormaPagamentoResponseDTO> cbFormaPgto;
    @FXML private AutoComplete<PessoaResponseDTO> inputFornec;
    @FXML private TextField inputData;
    @FXML private TextField inputValor;
    @FXML private TextArea inputObs;
    @FXML private Button btSalvar;
    @FXML private Button btCancel;
    @FXML private Text uuid;

    private DespesaResponseDTO despesaResponseDTO;

    public EditDespesaController(DespesaService despesaService,
                                 TipoDespesaService tipoDespesaService,
                                 FormaPagamentoService formaPagamentoService,
                                 PessoaService pessoaService)
    {
        this.despesaService = despesaService;
        this.tipoDespesaService = tipoDespesaService;
        this.formaPagamentoService = formaPagamentoService;
        this.pessoaService = pessoaService;
    }

    @FXML
    public void initialize()
    {
        cbTipoDespesa.setConfig(tipoDespesaService.all(), true);
        cbFormaPgto.setConfig(formaPagamentoService.all(), true);
        inputFornec.bind(
                text -> pessoaService.find(text, Sort.by("nome")),
                PessoaResponseDTO::nome,
                pessoa -> this.pessoaSelec = pessoa
        );
        CreateField.createDataField(inputData);
        CreateField.createMoneyField(inputValor);
    }

    public void setDespesa(DespesaResponseDTO despesaResponseDTO) {
        this.despesaResponseDTO = despesaResponseDTO;
        uuid.setText(despesaResponseDTO.uuid().toString());
        cbTipoDespesa.getSelectionModel().select(despesaResponseDTO.tipoDespesa());
        inputFornec.setText(despesaResponseDTO.fornecedor().nome());
        inputData.setText(FormatUtil.DATE_FORMAT.format(despesaResponseDTO.dataPagamento()));
        cbFormaPgto.getSelectionModel().select(despesaResponseDTO.formaPagamento());
        inputValor.setText(FormatUtil.CURRENCY_FORMAT.format(despesaResponseDTO.valor()));
        inputObs.setText(despesaResponseDTO.observacao());
    }

    public void salvar()
    {
        if(!Validator.isValidNode(inputFornec))
            Alerts.alertError("Campo Fornecedor inválido!");
        else if(!Validator.isValidDate(inputData.getText()))
            Alerts.alertError("Campo Data pagamento inválido!");
        else if(!Validator.isValidNode(inputValor))
            Alerts.alertError("Campo valor inválido!");
        else{
            TipoDespesaRefDTO tpdespesa = new TipoDespesaRefDTO(
                    cbTipoDespesa.getSelecionado().value(),
                    cbTipoDespesa.getSelecionado().nome());
            PessoaRefDTO pessoaRefDTO = new PessoaRefDTO(null, pessoaSelec.uuid());
            FormaPagamentoRefDTO formaPagamentoRefDTO = new FormaPagamentoRefDTO(null, cbFormaPgto.getValue().uuid());
            BigDecimal valorPgto = FormatUtil.parseMoedaBR(inputValor.getText());

            DespesaRequestUpdateDTO despesaRequestUpdateDTO = DespesaRequestUpdateDTO.builder()
                    .uuid(despesaResponseDTO.uuid())
                    .tipoDespesa(tpdespesa)
                    .fornecedor(pessoaRefDTO)
                    .dataPagamento(FormatUtil.stringParaData(inputData.getText()))
                    .formaPagamento(formaPagamentoRefDTO)
                    .valor(valorPgto)
                    .observacao(inputObs.getText())
                    .build();

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    despesaService.update(despesaRequestUpdateDTO);
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                Alerts.alertSucess("Despesa salva com sucesso!");
            });
            task.setOnFailed(e -> {
                Alerts.alertError("Houve um erro ao salvar essa Despesa!");
                task.getException().printStackTrace();
            });
            new Thread(task).start();
        }
    }
    @FXML
    private void fechar() {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }
}

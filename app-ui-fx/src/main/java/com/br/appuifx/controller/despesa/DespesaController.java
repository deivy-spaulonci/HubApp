package com.br.appuifx.controller.despesa;

import com.br.appuifx.SpringBootApp;
import com.br.appuifx.factory.CreateField;
import com.br.appuifx.util.Alerts;
import com.br.appuifx.util.FormatUtil;
import com.br.appuifx.util.TablesUtil;
import com.br.appuifx.util.Validator;
import com.br.appuifx.view.shared.AutoComplete;
import com.br.appuifx.view.shared.CbDefault;
import com.br.appuifx.view.shared.LoadingController;
import com.br.dto.request.create.DespesaRequestCreateDTO;
import com.br.dto.request.ref.FormaPagamentoRefDTO;
import com.br.dto.request.ref.PessoaRefDTO;
import com.br.dto.request.ref.TipoDespesaRefDTO;
import com.br.dto.response.DespesaResponseDTO;
import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.dto.response.PessoaResponseDTO;
import com.br.dto.response.TipoDespesaResponseDTO;
import com.br.entity.TipoDespesaEnum;
import com.br.filter.DespesaFilter;
import com.br.service.DespesaService;
import com.br.service.FormaPagamentoService;
import com.br.service.PessoaService;
import com.br.service.TipoDespesaService;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DespesaController {
    private DespesaService despesaService;
    private TipoDespesaService tipoDespesaService;
    private FormaPagamentoService formaPagamentoService;
    private PessoaService pessoaService;
    private PessoaResponseDTO pessoaSelec;
    private static ConfigurableApplicationContext context;
    /*======== FORM ========*/
    @FXML private CbDefault<TipoDespesaResponseDTO> cbTipoDespesa;
    @FXML private CbDefault<FormaPagamentoResponseDTO> cbFormaPgto;
    @FXML private AutoComplete<PessoaResponseDTO> inputFornec;
    @FXML private TextField inputData;
    @FXML private TextField inputValor;
    @FXML private TextArea inputObs;
    @FXML private Text total;
    @FXML private Text qtd;
    /*======== TABLE ========*/
    @FXML private TableView<DespesaResponseDTO> tableDespesa;
    @FXML private TableColumn<DespesaResponseDTO, Object> colTipo;
    @FXML private TableColumn<DespesaResponseDTO, Object> colFonecedor;
    @FXML private TableColumn<DespesaResponseDTO, LocalDate> colData;
    @FXML private TableColumn<DespesaResponseDTO, Object> colFormaPgto;
    @FXML private TableColumn<DespesaResponseDTO, BigDecimal> colValor;
    @FXML private TableColumn<DespesaResponseDTO, Object> colObs;
    @FXML private TableColumn<DespesaResponseDTO, Void> colEdit;
    @FXML private TableColumn<DespesaResponseDTO, Void> colDel;
    @FXML private LoadingController loadingController;

    @FXML private CbDefault<TipoDespesaResponseDTO> cbTipoDespesaFilter;
    @FXML private AutoComplete<PessoaResponseDTO> inputFornecFilter;
    @FXML private TextField inputInicio;
    @FXML private TextField inputTermino;


    public DespesaController(DespesaService despesaService,
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
        context = new SpringApplicationBuilder(SpringBootApp.class).run();
        cbTipoDespesa.setConfig(tipoDespesaService.all(), true);
        cbFormaPgto.setConfig(formaPagamentoService.all(), true);
        inputFornec.bind(
                text -> pessoaService.find(text, Sort.by("nome")),
                PessoaResponseDTO::nome,
                pessoa -> this.pessoaSelec = pessoa
        );
        CreateField.createDataField(inputData);
        CreateField.createMoneyField(inputValor);

        configurarColunas();
        loadoDespesas();
        initFilters();
    }

    public void initFilters()
    {
        cbTipoDespesaFilter.setConfig(tipoDespesaService.all(), false);
        inputFornecFilter.bind(
                text -> pessoaService.find(text, Sort.by("nome")),
                PessoaResponseDTO::nome,
                pessoa -> this.pessoaSelec = pessoa
        );
        CreateField.createDataField(inputInicio);
        CreateField.createDataField(inputTermino);
    }

    private void configurarColunas()
    {
        TablesUtil.bind(colTipo, "tipoDespesa.nome");
        TablesUtil.bind(colFonecedor, "fornecedor.nome");
        TablesUtil.bindData(colData, "dataPagamento");
        TablesUtil.bind(colFormaPgto, "formaPagamentoDesc");
        TablesUtil.bindMoney(colValor, "valor");
        TablesUtil.bind(colObs, "observacao");
        TablesUtil.btColEditar(colEdit, item -> editar());
        TablesUtil.btColDelete(colDel, item -> excluir());
        colData.setSortType(TableColumn.SortType.DESCENDING);
        tableDespesa.getSortOrder().add(colData);
        TablesUtil.bindDoubleClick(tableDespesa, item -> editar());
    }

    public void loadoDespesas()
    {
        loadingController.show();
        List<TipoDespesaEnum> tipoDespesaEnumList = new ArrayList<>();
        if(cbTipoDespesaFilter.isSelecionado()){
            tipoDespesaEnumList.add(TipoDespesaEnum.valueOf(cbTipoDespesaFilter.getSelecionado().value()));
        }

        DespesaFilter filter = DespesaFilter.builder()
                .tipos(tipoDespesaEnumList)
                .fornecedor(pessoaSelec)
                .inicio(FormatUtil.stringParaData(inputInicio.getText()))
                .termino(FormatUtil.stringParaData(inputTermino.getText()))
                .build();

        Task<List<DespesaResponseDTO>> task = new Task<>() {
            @Override
            protected List<DespesaResponseDTO> call() {
                return despesaService.all(filter);
            }
        };
        task.setOnSucceeded(e -> {
            List<DespesaResponseDTO> lista = task.getValue();
            total.setText("Total: " + FormatUtil.CURRENCY_FORMAT.format(
                    lista.stream()
                    .map(DespesaResponseDTO::valor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)));
            qtd.setText("Registros: " + lista.size());
            tableDespesa.setItems(FXCollections.observableArrayList(lista));
            tableDespesa.sort();
            loadingController.hide();
        });
        task.setOnFailed(e -> {
            loadingController.hide();
            task.getException().printStackTrace();
        });
        new Thread(task).start();
    }

    public void resetFilter()
    {
        cbTipoDespesaFilter.reset();
        inputFornecFilter.clear();
        inputInicio.clear();
        inputTermino.clear();
        loadoDespesas();
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
            loadingController.show();

            TipoDespesaRefDTO tpdespesa = new TipoDespesaRefDTO(
                    cbTipoDespesa.getSelecionado().value(),
                    cbTipoDespesa.getSelecionado().nome());
            PessoaRefDTO pessoaRefDTO = new PessoaRefDTO(null, pessoaSelec.uuid());
            FormaPagamentoRefDTO formaPagamentoRefDTO = new FormaPagamentoRefDTO(null, cbFormaPgto.getValue().uuid());
            BigDecimal valorPgto = FormatUtil.parseMoedaBR(inputValor.getText());

            DespesaRequestCreateDTO despesaRequestCreateDTO = DespesaRequestCreateDTO.builder()
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
                    despesaService.create(despesaRequestCreateDTO);
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                despesaService.create(despesaRequestCreateDTO);
                loadingController.hide();
                Alerts.alertSucess("Despesa salva com sucesso!");
            });
            task.setOnFailed(e -> {
                loadingController.hide();
                task.getException().printStackTrace();
            });
            new Thread(task).start();
        }
    }

    @FXML
    public void editar()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/despesa/formEditDespesa.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            EditDespesaController controller = loader.getController();
            controller.setDespesa(tableDespesa.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tableDespesa.getScene().getWindow());

            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluir()
    {
        DespesaResponseDTO item = tableDespesa.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja excluir?");
        alert.setContentText(" Despesa : " + item.tipoDespesa().nome());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            despesaService.delete(item.uuid());
            loadoDespesas();
        }
    }
}

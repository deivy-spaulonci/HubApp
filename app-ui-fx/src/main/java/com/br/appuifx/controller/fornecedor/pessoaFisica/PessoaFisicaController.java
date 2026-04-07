package com.br.appuifx.controller.fornecedor.pessoaFisica;

import com.br.appuifx.SpringBootApp;
import com.br.appuifx.dto.CidadeDTO;
import com.br.appuifx.dto.Estado;
import com.br.appuifx.factory.CreateField;
import com.br.appuifx.service.IbgeService;
import com.br.appuifx.util.Alerts;
import com.br.appuifx.util.TablesUtil;
import com.br.appuifx.util.Validator;
import com.br.appuifx.view.shared.LoadingController;
import com.br.dto.request.create.PessoaFisicaRequestCreateDTO;
import com.br.dto.response.PessoaFisicaResponseDTO;
import com.br.service.PessoaFisicaService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class PessoaFisicaController {
    private static ConfigurableApplicationContext context;
    private PessoaFisicaService pessoaFisicaService;
    @FXML private LoadingController loadingController;
    /*======== FORM ========*/
    @FXML private TextField inputNome;
    @FXML private TextField inputCpf;
    @FXML private ComboBox<Estado> cbEstado;
    @FXML private ComboBox<CidadeDTO> cbCidade;
    @FXML private Button btSalvar;
    /*======== TABLE ========*/
    @FXML private TableView<PessoaFisicaResponseDTO> tablePessoaFisica;
    @FXML private TableColumn<PessoaFisicaResponseDTO, Object> colNome;
    @FXML private TableColumn<PessoaFisicaResponseDTO, Object> colCpf;
    @FXML private TableColumn<PessoaFisicaResponseDTO, Void> colEdit;

    @FXML private TextField inputBusca = new TextField();

    public PessoaFisicaController(PessoaFisicaService pessoaFisicaService)
    {
        this.pessoaFisicaService = pessoaFisicaService;
    }
    @FXML
    public void initialize()
    {
        context = new SpringApplicationBuilder(SpringBootApp.class).run();
        CreateField.createCbEstado(cbEstado, this::loadCidades);
        CreateField.createCbCidade(cbCidade);
        btSalvar.setOnAction(e -> salvar());
        inputBusca.textProperty().addListener((obs, oldVal, newVal) -> loadPessoas());
        loadCidades(Estado.SP.name());
        configurarColunas();
        loadPessoas();
    }

    private void configurarColunas()
    {
        TablesUtil.bind(colNome, "nome");
        TablesUtil.bind(colCpf, "cpf");
        TablesUtil.btColEditar(colEdit, item -> editar());
        colNome.setSortType(TableColumn.SortType.ASCENDING);
        tablePessoaFisica.getSortOrder().add(colNome);
        TablesUtil.bindDoubleClick(tablePessoaFisica, item -> editar());
    }

    public void salvar()
    {
        if(!Validator.isValidNode(inputNome))
            Alerts.alertError("Campo Nome inválido!");
        else if(cbCidade.getSelectionModel().getSelectedItem()==null)
            Alerts.alertError("Campo Cidade inválido!");
        else{
            loadingController.show();

            CidadeDTO cidadeDTO = cbCidade.getSelectionModel().getSelectedItem();
            PessoaFisicaRequestCreateDTO pessoaFisicaRequestCreateDTO =
                    new PessoaFisicaRequestCreateDTO(
                            inputNome.getText(),inputCpf.getText(),
                            cidadeDTO.ibge().toString(),
                            true
                    );

            Task<Void> task  = new Task<>() {
                @Override
                protected Void call() {
                    pessoaFisicaService.save(pessoaFisicaRequestCreateDTO);
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                reset();
                loadingController.hide();
                loadPessoas();
                Alerts.alertSucess("Pessoa Fisíca salva com sucesso!");
            });
            task.setOnFailed(e -> {
                loadingController.hide();
                Alerts.alertError("Houve um erro ao salvar essa pessoa!");
                task.getException().printStackTrace();
            });
            new Thread(task).start();
        }
    }

    void loadPessoas()
    {
        loadingController.show();
        Task<List<PessoaFisicaResponseDTO>> task = new Task<>() {
            @Override
            protected List<PessoaFisicaResponseDTO> call() {
                return pessoaFisicaService.findByNomeOrCpf(inputBusca.getText());
            }
        };
        task.setOnSucceeded(e -> {
            List<PessoaFisicaResponseDTO> lista = task.getValue();
            tablePessoaFisica.setItems(FXCollections.observableArrayList(lista));
            tablePessoaFisica.sort();
            loadingController.hide();
        });
        task.setOnFailed(e -> {
            loadingController.hide();;
            task.getException().printStackTrace();
        });
        new Thread(task).start();
    }

    void loadCidades(String sigla)
    {
        loadingController.show();
        Task<Void> task = new Task<>() {
            IbgeService service = new IbgeService();
            @Override
            protected Void call() {
                CompletableFuture.supplyAsync(() -> service.buscarCidadesPorUF(sigla))
                        .thenAccept(cidades -> Platform.runLater(() -> {
                            cbCidade.getItems().setAll(cidades);
                            cbCidade.getSelectionModel().selectFirst();
                        }));
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            cbCidade.setDisable(false);
            loadingController.hide();
        });
        task.setOnFailed(e -> {
            cbCidade.setDisable(false);
            loadingController.hide();
            task.getException().printStackTrace();
        });
        new Thread(task).start();
    }

    void reset()
    {
        inputNome.clear();
        inputCpf.clear();
    }

    @FXML
    public void editar()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fornecedor/pessoaFisica/formEditPessoaFisica.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            EditPessoaFisicaController controller = loader.getController();
            controller.setPessoaFisica(tablePessoaFisica.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tablePessoaFisica.getScene().getWindow());

            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

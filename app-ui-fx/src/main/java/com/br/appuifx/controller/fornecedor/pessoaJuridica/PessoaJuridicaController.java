package com.br.appuifx.controller.fornecedor.pessoaJuridica;

import com.br.appuifx.SpringBootApp;
import com.br.appuifx.controller.despesa.EditDespesaController;
import com.br.appuifx.dto.EmpresaDTO;
import com.br.appuifx.factory.CreateField;
import com.br.appuifx.service.EmpresaService;
import com.br.appuifx.util.Alerts;
import com.br.appuifx.util.TablesUtil;
import com.br.appuifx.util.Validator;
import com.br.appuifx.view.shared.LoadingController;
import com.br.dto.request.create.PessoaJuridicaRequestCreateDTO;
import com.br.dto.response.PessoaJuridicaResponseDTO;
import com.br.service.PessoaJuridicaService;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PessoaJuridicaController {
    private static ConfigurableApplicationContext context;
    private PessoaJuridicaService pessoaJuridicaService;
    /*======== FORM ========*/
    private EmpresaDTO empresaDTO;
    @FXML public TextField inputCnpj;
    @FXML public TextField inputNome;
    @FXML public TextField inputRazaoSocial;
    @FXML public TextField lblCidade;
    @FXML public TextField lblUf;
    @FXML public Button btSalvar;
    /*======== TABLE ========*/
    @FXML private TableView<PessoaJuridicaResponseDTO> tablePj;
    @FXML private TableColumn<PessoaJuridicaResponseDTO, Object> colNome;
    @FXML private TableColumn<PessoaJuridicaResponseDTO, Object> colRazao;
    @FXML private TableColumn<PessoaJuridicaResponseDTO, Object> colCnpj;
    @FXML private TableColumn<PessoaJuridicaResponseDTO, Void> colEdit;
    @FXML public TextField inputBusca;

    @FXML private LoadingController loadingController;

    public PessoaJuridicaController(PessoaJuridicaService pessoaJuridicaService)
    {
        this.pessoaJuridicaService = pessoaJuridicaService;
    }

    @FXML
    public void initialize()
    {
        context = new SpringApplicationBuilder(SpringBootApp.class).run();
        CreateField.applyCnpjMask(inputCnpj);
        configurarColunas();
        inputBusca.textProperty().addListener((obs, oldVal, newVal) -> loadoPessoas());
        loadoPessoas();
    }

    public void configurarColunas()
    {
        TablesUtil.bind(colNome, "nome");
        TablesUtil.bind(colRazao, "razaoSocial");
        TablesUtil.bind(colCnpj, "cnpj");
        TablesUtil.btColEditar(colEdit, item -> editar());
        colNome.setSortType(TableColumn.SortType.DESCENDING);
        tablePj.getSortOrder().add(colNome);
        TablesUtil.bindDoubleClick(tablePj, item -> editar());
    }

    public void loadoPessoas()
    {
        loadingController.show();
        Task<List<PessoaJuridicaResponseDTO>> task = new Task<>() {
            @Override
            protected List<PessoaJuridicaResponseDTO> call() {
                return pessoaJuridicaService.findByNomeOrCnpjOrRazaoPaged(inputBusca.getText());
            }
        };
        task.setOnSucceeded(e -> {
            tablePj.setItems(FXCollections.observableArrayList(task.getValue()));
            tablePj.sort();
            loadingController.hide();
        });
        task.setOnFailed(e -> {
            loadingController.hide();;
            task.getException().printStackTrace();
        });
        new Thread(task).start();
    }

    public void salvar()
    {
        if(pessoaJuridicaService.findByCNPJ(inputCnpj.getText().replaceAll("\\D", "")).isPresent())
            Alerts.alertError("CNPJ ja cadastrado!");
        else if(!Validator.isValidNode(inputCnpj) && inputCnpj.getText().length()!=18)
            Alerts.alertError("Campo CNPJ inválido!");
        else if(!Validator.isValidNode(inputNome))
            Alerts.alertError("Campo Nome inválido!");
        else if(!Validator.isValidNode(inputRazaoSocial))
            Alerts.alertError("Campo Razão Social inválido!");
        else{
            loadingController.show();
            PessoaJuridicaRequestCreateDTO pessoaJuridicaRequestCreateDTO = PessoaJuridicaRequestCreateDTO.builder()
                    .nome(inputNome.getText())
                    .razaoSocial(inputRazaoSocial.getText())
                    .cnpj(inputCnpj.getText().replaceAll("\\D", ""))
                    .codigoIbge(empresaDTO.codigoIbge())
                    .build();
            Task<Void> task  = new Task<>() {
                @Override
                protected Void call() {
                    pessoaJuridicaService.save(pessoaJuridicaRequestCreateDTO);
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                reset();
                loadingController.hide();
                loadoPessoas();
                Alerts.alertSucess("Pessoa Jurídica salva com sucesso!");
            });
            task.setOnFailed(e -> {
                loadingController.hide();
                Alerts.alertError("Houve um erro ao salvar essa pessoa jurídica!");
                task.getException().printStackTrace();
            });
            new Thread(task).start();

        }
    }

    public void setFields()
    {
        inputNome.setText(empresaDTO.nomeFantasia()=="null" ? empresaDTO.razaoSocial() : empresaDTO.nomeFantasia());
        inputRazaoSocial.setText(empresaDTO.razaoSocial());
        lblUf.setText(empresaDTO.municipioDTO().uf());
        lblCidade.setText(empresaDTO.municipioDTO().nome());
    }

    public void searchCnpj()
    {
        if(inputCnpj.getText().length() == 18){
            loadingController.show();
            Task<EmpresaDTO> task = new Task<>() {
                @Override
                protected EmpresaDTO call() {
                    return EmpresaService.buscarCnpj(inputCnpj.getText());
                }
            };
            task.setOnSucceeded(e -> {
                empresaDTO = task.getValue();
                setFields();
                loadingController.hide();
            });
            task.setOnFailed(e -> {
                loadingController.hide();
                task.getException().printStackTrace();
            });
            new Thread(task).start();
        }
    }

    void reset()
    {
        inputCnpj.clear();
        inputNome.clear();
        inputRazaoSocial.clear();
        lblUf.clear();
        lblCidade.clear();
    }

    @FXML
    public void editar()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fornecedor/pessoaJuridica/formEditPessoaJuridica.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            EditPessoaJuridicaController controller = loader.getController();
            controller.setPessoaJuridica(tablePj.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tablePj.getScene().getWindow());

            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

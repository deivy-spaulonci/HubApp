package com.br.appuifx.controller.fornecedor.pessoaFisica;

import com.br.appuifx.dto.CidadeDTO;
import com.br.appuifx.dto.MunicipioDTO;
import com.br.appuifx.factory.CreateField;
import com.br.appuifx.service.IbgeService;
import com.br.appuifx.util.Alerts;
import com.br.appuifx.dto.Estado;
import com.br.appuifx.util.Validator;
import com.br.appuifx.view.shared.LoadingController;
import com.br.appuifx.view.shared.LoadingManager;
import com.br.dto.request.update.PessoaFisicaRequestUpdateDTO;
import com.br.dto.response.PessoaFisicaResponseDTO;
import com.br.service.PessoaFisicaService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class EditPessoaFisicaController {
    private PessoaFisicaService pessoaFisicaService;
    @FXML private LoadingController loadingController;
    @FXML private Button btSalvar;
    @FXML private Button btCancel;
    @FXML private Text uuid;
    @FXML private TextField inputNome;
    @FXML private TextField inputCpf;
    @FXML private ComboBox<Estado> cbEstado;
    @FXML private ComboBox<CidadeDTO> cbCidade;
    private PessoaFisicaResponseDTO pessoaFisicaResponseDTOEdit;

    public EditPessoaFisicaController(PessoaFisicaService pessoaFisicaService)
    {
        this.pessoaFisicaService = pessoaFisicaService;
    }

    @FXML
    public void initialize()
    {
        CreateField.createCbEstado(cbEstado, this::loadCidades);
        CreateField.createCbCidade(cbCidade);
        btSalvar.setOnAction(e -> salvar());
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
                            if(pessoaFisicaResponseDTOEdit.codigoIbge()==null)
                                cbCidade.getSelectionModel().selectFirst();
                            else
                                cbCidade.getSelectionModel().select(
                                        cbCidade.getItems().stream()
                                                .filter(c -> c.ibge().equals(pessoaFisicaResponseDTOEdit.codigoIbge()))
                                                .findFirst().get());

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

    public void salvar()
    {
        if(!Validator.isValidNode(inputNome))
            Alerts.alertError("Campo Nome inválido!");
        else if(cbCidade.getSelectionModel().getSelectedItem()==null)
            Alerts.alertError("Campo Cidade inválido!");
        else{
            loadingController.show();
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                   CidadeDTO cidadeDTO = cbCidade.getSelectionModel().getSelectedItem();
                   PessoaFisicaRequestUpdateDTO pessoaFisicaRequestUpdateDTO = PessoaFisicaRequestUpdateDTO.builder()
                           .uuid(pessoaFisicaResponseDTOEdit.uuid())
                           .nome(inputNome.getText())
                           .cpf(inputCpf.getText())
                           .codigoIbge(cidadeDTO.ibge())
                           .build();
                    pessoaFisicaService.update(pessoaFisicaRequestUpdateDTO);
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                loadingController.hide();
                fechar();
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
    public void setPessoaFisica(PessoaFisicaResponseDTO pessoaFisicaResponseDTO)
    {
        this.pessoaFisicaResponseDTOEdit = pessoaFisicaResponseDTO;
        uuid.setText(pessoaFisicaResponseDTOEdit.uuid().toString());
        inputNome.setText(pessoaFisicaResponseDTOEdit.nome());
        inputCpf.setText(pessoaFisicaResponseDTOEdit.cpf());
        IbgeService ibgeService = new IbgeService();
        MunicipioDTO municipioDTO = ibgeService.buscarCidadePorIbge(pessoaFisicaResponseDTO.codigoIbge());
        cbEstado.getSelectionModel().select(Estado.valueOf(municipioDTO.uf()));
        loadCidades(municipioDTO.uf());
    }

    @FXML
    private void fechar() {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }
}

package com.br.appuifx.controller.fornecedor.pessoaJuridica;

import com.br.appuifx.dto.MunicipioDTO;
import com.br.appuifx.factory.CreateField;
import com.br.appuifx.service.IbgeService;
import com.br.appuifx.util.Alerts;
import com.br.appuifx.util.Validator;
import com.br.appuifx.view.shared.LoadingController;
import com.br.dto.request.update.PessoaJuridicaRequestUpdateDTO;
import com.br.dto.response.PessoaJuridicaResponseDTO;
import com.br.service.PessoaJuridicaService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class EditPessoaJuridicaController {
    private PessoaJuridicaService pessoaJuridicaService;
    private PessoaJuridicaResponseDTO pessoaJuridicaResponseDTO;
    /*======== FORM ========*/
    @FXML public TextField inputCnpj;
    @FXML public TextField inputNome;
    @FXML public TextField inputRazaoSocial;
    @FXML public TextField lblCidade;
    @FXML public TextField lblUf;
    @FXML private Button btSalvar;
    @FXML private Button btCancel;
    @FXML private Text uuid;

    @FXML private LoadingController loadingController;


    public EditPessoaJuridicaController(PessoaJuridicaService pessoaJuridicaService)
    {
        this.pessoaJuridicaService = pessoaJuridicaService;
    }

    @FXML
    public void initialize()
    {
        CreateField.applyCnpjMask(inputCnpj);
    }

    public void setPessoaJuridica(PessoaJuridicaResponseDTO pessoaJuridicaResponseDTO)
    {
        this.pessoaJuridicaResponseDTO = pessoaJuridicaResponseDTO;
        uuid.setText(pessoaJuridicaResponseDTO.uuid().toString());
        if(pessoaJuridicaResponseDTO.cnpj()==null || pessoaJuridicaResponseDTO.cnpj().isEmpty())
            inputCnpj.setDisable(false);
        else {
            inputCnpj.setText(pessoaJuridicaResponseDTO.cnpj());
            inputCnpj.setDisable(true);
        }
        inputNome.setText(pessoaJuridicaResponseDTO.nome());
        inputRazaoSocial.setText(pessoaJuridicaResponseDTO.razaoSocial());
        IbgeService ibgeService = new IbgeService();
        MunicipioDTO municipioDTO = ibgeService.buscarCidadePorIbge(pessoaJuridicaResponseDTO.codigoIbge());
        lblUf.setText(municipioDTO.uf());
        lblCidade.setText(municipioDTO.nome());

    }

    @FXML
    private void fechar() {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
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
        else {
            loadingController.show();
            PessoaJuridicaRequestUpdateDTO pessoaJuridicaRequestUpdateDTO = PessoaJuridicaRequestUpdateDTO.builder()
                    .uuid(pessoaJuridicaResponseDTO.uuid())
                    .cnpj(inputCnpj.getText().replaceAll("\\D", ""))
                    .nome(inputNome.getText())
                    .razaoSocial(inputRazaoSocial.getText())
                    .codigoIbge(pessoaJuridicaResponseDTO.codigoIbge())
                    .build();

            Task<Void> task  = new Task<>() {
                @Override
                protected Void call() {
                    pessoaJuridicaService.update(pessoaJuridicaRequestUpdateDTO);
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                loadingController.hide();
                Alerts.alertSucess("Pessoa Jurídica salva com sucesso!");
                fechar();
            });
            task.setOnFailed(e -> {
                loadingController.hide();
                Alerts.alertError("Houve um erro ao salvar essa pessoa jurídica!");
                task.getException().printStackTrace();
            });
            new Thread(task).start();
        }
    }
}

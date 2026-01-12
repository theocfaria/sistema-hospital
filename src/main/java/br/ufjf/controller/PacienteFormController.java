package br.ufjf.controller;

import br.ufjf.model.Pacient;
import br.ufjf.model.StatusInternacao;
import br.ufjf.repository.PacientRepository;
import br.ufjf.helpers.AlertExibitor;
import br.ufjf.helpers.CpfValidator;
import br.ufjf.helpers.TelefoneValidator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PacienteFormController implements Initializable {
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtSenha;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;
    @FXML private ComboBox<StatusInternacao> cbStatus;


    private ObservableList<Pacient> listaReferencia;

    protected void setLista(ObservableList<Pacient> lista){
        listaReferencia = lista;
    }

    @FXML
    private void handleNovoPaciente(){

        PacientRepository pacientRepository = new PacientRepository();

        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty() || txtSenha.getText().isEmpty()) {
            AlertExibitor.exibirAlerta("Preencha todos os campos obrigatórios!");
            return;
        }

        if(!CpfValidator.validaCpf(txtCpf.getText())){
            AlertExibitor.exibirAlerta("CPF inválido");
            return;
        }

        if(pacientRepository.checkCpfExists(txtCpf.getText())){
            AlertExibitor.exibirAlerta("CPF já registrado");
            return;
        }

        if(!txtTelefone.getText().isEmpty()) {
            if (!TelefoneValidator.validaTelefone(txtTelefone.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Telefone Inválido!");
                return;
            }
        }

        String telefone = TelefoneValidator.formatarTelefone(txtTelefone.getText());

        Pacient novo = new Pacient(txtNome.getText(), txtCpf.getText(), txtSenha.getText(), txtEmail.getText(), telefone, txtEndereco.getText(), null, cbStatus.getValue());

        listaReferencia.add(novo);

        pacientRepository.saveAll(listaReferencia);

        fecharJanela();
    }

    @FXML
    private void handleCancelar(){
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbStatus.getItems().setAll(StatusInternacao.values());
        cbStatus.setValue(StatusInternacao.NAO_INTERNADO);
    }
}

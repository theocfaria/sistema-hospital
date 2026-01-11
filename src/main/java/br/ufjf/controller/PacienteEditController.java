package br.ufjf.controller;

import br.ufjf.model.Pacient;
import br.ufjf.model.StatusInternacao;
import br.ufjf.repository.PacientRepository;
import br.ufjf.utils.CpfValidator;
import br.ufjf.utils.TelefoneValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PacienteEditController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtSenha;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;
    @FXML private ComboBox<StatusInternacao> cbStatus;
    @FXML private Button btnSalvar;
    @FXML private Button btnCancelar;

    private Pacient pacienteSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbStatus.getItems().setAll(StatusInternacao.values());
    }

    public void setPaciente(Pacient paciente){
        this.pacienteSelecionado = paciente;

        txtNome.setText(paciente.getName());
        txtCpf.setText(paciente.getCpf());
        txtSenha.setText(paciente.getPassword());
        txtEmail.setText(paciente.getEmail());
        txtTelefone.setText(paciente.getTelefone());
        txtEndereco.setText(paciente.getEndereco());
        cbStatus.setValue(paciente.getStatusInternacao());
    }

    @FXML
    private void handleEditarPaciente(){
        PacientRepository pacienteRepository = new PacientRepository();

        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty() || txtSenha.getText().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Preencha todos os campos obrigatórios!").show();
            return;
        }

        if(!txtCpf.getText().equals(pacienteSelecionado.getCpf())) {
            if (!CpfValidator.validaCpf(txtCpf.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "CPF Inválido !").show();
                return;
            }

            if(pacienteRepository.checkCpfExists(txtCpf.getText())){
                new Alert(Alert.AlertType.INFORMATION, "CPF já registrado !").show();
                return;
            }
        }

        if(txtTelefone != null && txtTelefone.getText() != null && !txtTelefone.getText().isEmpty()) {
            if (!TelefoneValidator.validaTelefone(txtTelefone.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Telefone Inválido!").show();
                return;
            }
        }

        String telefoneFormatado = TelefoneValidator.formatarTelefone(txtTelefone.getText());

        pacienteSelecionado.setName(txtNome.getText());
        pacienteSelecionado.setCpf(txtCpf.getText());
        pacienteSelecionado.setPassword(txtSenha.getText());
        pacienteSelecionado.setEmail(txtEmail.getText());
        pacienteSelecionado.setTelefone(telefoneFormatado);
        pacienteSelecionado.setEndereco(txtEndereco.getText());
        pacienteSelecionado.setStatusInternacao(cbStatus.getValue());

        pacienteRepository.update(pacienteSelecionado);

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
}
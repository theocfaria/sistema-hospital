package br.ufjf.controller;

import br.ufjf.model.Medico;
import br.ufjf.model.Pacient;
import br.ufjf.repository.MedicoRepository;
import br.ufjf.repository.PacientRepository;
import br.ufjf.utils.CpfValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PacienteEditController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtSenha;
    @FXML private Button btnSalvar;
    @FXML private Button btnCancelar;

    Pacient pacienteSelecionado;

    public void setPaciente(Pacient paciente){
        pacienteSelecionado = paciente;
        txtNome.setText(paciente.getName());
        txtCpf.setText(paciente.getCpf());
        txtSenha.setText(paciente.getPassword());
    }

    @FXML
    private void handleEditarPaciente(){

        PacientRepository pacienteRepository = new PacientRepository();

        if(!txtCpf.getText().equals(pacienteSelecionado.getCpf())) {
            if (!CpfValidator.validaCpf(txtCpf.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "CPF Inválido !");
                alert.show();
                return;
            }

            if(pacienteRepository.checkCpfExists(txtCpf.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "CPF já registrado !");
                alert.show();
                return;
            }
        }

        pacienteSelecionado.setName(txtNome.getText());
        pacienteSelecionado.setPassword(txtSenha.getText());
        pacienteSelecionado.setCpf(txtCpf.getText());

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

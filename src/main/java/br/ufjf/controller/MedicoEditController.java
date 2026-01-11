package br.ufjf.controller;

import br.ufjf.model.Medico;
import br.ufjf.model.User;
import br.ufjf.repository.MedicoRepository;
import br.ufjf.utils.CpfValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MedicoEditController {
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtSenha;
    @FXML private Button btnSalvar;
    @FXML private Button btnCancelar;

    Medico medicoSelecionado;

    public void setMedico(Medico medico){
        medicoSelecionado = medico;
        txtNome.setText(medico.getName());
        txtCpf.setText(medico.getCpf());
        txtSenha.setText(medico.getPassword());
    }

    @FXML
    private void handleEditarMedico(){

        MedicoRepository medicoRepository = new MedicoRepository();

        if(txtCpf.getText() != medicoSelecionado.getCpf()) {
            if (!CpfValidator.validaCpf(txtCpf.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "CPF Inválido !");
                alert.show();
                return;
            }

            if(medicoRepository.checkCpfExists(txtCpf.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "CPF já registrado !");
                alert.show();
                return;
            }
        }

        medicoSelecionado.setName(txtNome.getText());
        medicoSelecionado.setPassword(txtSenha.getText());
        medicoSelecionado.setCpf(txtCpf.getText());

        medicoRepository.update(medicoSelecionado);

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

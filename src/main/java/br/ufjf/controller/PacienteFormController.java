package br.ufjf.controller;

import br.ufjf.model.Pacient;
import br.ufjf.repository.PacientRepository;
import br.ufjf.utils.CpfValidator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PacienteFormController {
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtSenha;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;

    private ObservableList<Pacient> listaReferencia;

    protected void setLista(ObservableList<Pacient> lista){
        listaReferencia = lista;
    }

    @FXML
    private void handleNovoPaciente(){

        PacientRepository pacientRepository = new PacientRepository();

        if(!CpfValidator.validaCpf(txtCpf.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "CPF inválido");
            alert.show();
            return;
        }

        if(pacientRepository.checkCpfExists(txtCpf.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "CPF já registrado !");
            alert.show();
            return;
        }

        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty() || txtSenha.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preencha todos os campos!");
            return;
        }

        Pacient novo = new Pacient(txtNome.getText(), txtCpf.getText(), txtSenha.getText());

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
}

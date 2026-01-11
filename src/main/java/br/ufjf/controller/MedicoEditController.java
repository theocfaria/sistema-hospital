package br.ufjf.controller;

import br.ufjf.model.Medico;
import br.ufjf.model.StatusAtendimento;
import br.ufjf.repository.MedicoRepository;
import br.ufjf.helpers.AlertExibitor;
import br.ufjf.helpers.CpfValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MedicoEditController {
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtSenha;
    @FXML private Button btnSalvar;
    @FXML private ComboBox<StatusAtendimento> cbStatus;
    @FXML private Button btnCancelar;

    Medico medicoSelecionado;

    @FXML
    public void initialize() {
        cbStatus.getItems().setAll(StatusAtendimento.values());
    }

    public void setMedico(Medico medico){
        medicoSelecionado = medico;
        txtNome.setText(medico.getName());
        txtCpf.setText(medico.getCpf());
        txtSenha.setText(medico.getPassword());
        cbStatus.setValue(medico.getStatusAtendimento());
    }

    @FXML
    private void handleEditarMedico(){

        MedicoRepository medicoRepository = new MedicoRepository();

        if(!txtCpf.getText().equals(medicoSelecionado.getCpf())) {
            if (!CpfValidator.validaCpf(txtCpf.getText())) {
                AlertExibitor.exibirAlerta("CPF Inválido!");
                return;
            }

            if(medicoRepository.checkCpfExists(txtCpf.getText())){
                AlertExibitor.exibirAlerta("CPF já registrado!");
                return;
            }
        }

        medicoSelecionado.setName(txtNome.getText());
        medicoSelecionado.setPassword(txtSenha.getText());
        medicoSelecionado.setCpf(txtCpf.getText());
        medicoSelecionado.setStatusAtendimento(cbStatus.getValue());

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

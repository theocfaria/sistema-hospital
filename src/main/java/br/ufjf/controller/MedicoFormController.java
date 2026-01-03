package br.ufjf.controller;

import br.ufjf.model.Medico;
import br.ufjf.repository.MedicoRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class MedicoFormController {
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtSenha;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;

    private ObservableList<Medico> listaReferencia;

    protected void setLista(ObservableList<Medico> lista){
        listaReferencia = lista;
    }

    @FXML
    private void handleNovoMedico(){

        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty() || txtSenha.getText().isEmpty()) {
            System.out.println("Erro: Preencha todos os campos.");
            return;
        }

        Medico novo = new Medico(txtNome.getText(), txtCpf.getText(), txtSenha.getText());

        listaReferencia.add(novo);

        MedicoRepository medicoRepository = new MedicoRepository();
        medicoRepository.saveAll(listaReferencia);

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

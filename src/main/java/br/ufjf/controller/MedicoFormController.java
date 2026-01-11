package br.ufjf.controller;

import br.ufjf.model.Medico;
import br.ufjf.model.StatusAtendimento;
import br.ufjf.model.StatusInternacao;
import br.ufjf.model.User;
import br.ufjf.repository.MedicoRepository;
import br.ufjf.utils.AlertExibitor;
import br.ufjf.utils.CpfValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MedicoFormController implements Initializable {
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtSenha;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;
    @FXML private ComboBox<StatusAtendimento> cbStatus;

    private ObservableList<Medico> listaReferencia;

    protected void setLista(ObservableList<Medico> lista){
        listaReferencia = lista;
    }

    @FXML
    private void handleNovoMedico(){

        MedicoRepository medicoRepository = new MedicoRepository();

        if(!CpfValidator.validaCpf(txtCpf.getText())){
            AlertExibitor.exibirAlerta("CPF Inválido!");
            return;
        }

        if(medicoRepository.checkCpfExists(txtCpf.getText())){
            AlertExibitor.exibirAlerta("CPF já registrado!");
            return;
        }

        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty() || txtSenha.getText().isEmpty()) {
            AlertExibitor.exibirAlerta("Preencha todos os campos !");
            return;
        }

        List<DayOfWeek> listaDiasAtendimento = new ArrayList<>();

        Medico novo = new Medico(txtNome.getText(), txtCpf.getText(), txtSenha.getText(), listaDiasAtendimento, "08:00", "16:00", "30",  cbStatus.getValue());

        listaReferencia.add(novo);

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbStatus.getItems().setAll(StatusAtendimento.values());
        cbStatus.setValue(StatusAtendimento.DISPONIVEL);
    }
}

package br.ufjf.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ufjf.model.Medico;
import br.ufjf.model.User;
import br.ufjf.repository.MedicoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class AgendarConsultaController implements DashboardController, Initializable {
    @FXML
    private ComboBox<String> doctorComboBox;
    private User user;

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MedicoRepository repo = new MedicoRepository();

        List<Medico> medicos = repo.loadAll();
        List<String> nomeMedicos = new ArrayList<>();

        for (Medico medico : medicos) {
            nomeMedicos.add(medico.getName());
        }

        ObservableList<String> m = FXCollections.observableArrayList(nomeMedicos);
        doctorComboBox.setItems(m);
    }
}

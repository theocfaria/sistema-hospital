package br.ufjf.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.ufjf.model.Pacient;
import br.ufjf.model.Slot;
import br.ufjf.model.StatusConsulta;
import br.ufjf.repository.PacientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VerificarVisitasController implements Initializable, DashboardController<Pacient> {
    private Pacient user;
    PacientRepository repo = new PacientRepository();
    private ObservableList<Pacient> listaResultados = FXCollections.observableArrayList();

    @FXML
    private TextField patientTextField;
    @FXML
    private TableView<Pacient> visitasTable;
    @FXML
    private TableColumn<Pacient, String> colNome;
    @FXML
    private TableColumn<Pacient, StatusConsulta> colStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusInternacao"));

        visitasTable.setItems(listaResultados);
    }

    @Override
    public void setUser(Pacient user) {
        this.user = user;
    }

    @FXML
    public void pesquisaPaciente() {
        String busca = patientTextField.getText().trim();

        if (busca.isEmpty()) {
            listaResultados.clear();
            return;
        }

        List<Pacient> encontrados = repo.findManyByName(busca);

        if (encontrados != null) {
            listaResultados.setAll(encontrados);
        } else {
            listaResultados.clear();
        }
    }
}

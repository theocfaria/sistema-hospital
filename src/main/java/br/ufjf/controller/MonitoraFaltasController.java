package br.ufjf.controller;

import br.ufjf.model.Consulta;
import br.ufjf.model.StatusConsulta;
import br.ufjf.repository.ConsultaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MonitoraFaltasController {

    @FXML private TableView<Consulta> tableFaltas;
    @FXML private TableColumn<Consulta, String> colPaciente;
    @FXML private TableColumn<Consulta, String> colMedico;
    @FXML private TableColumn<Consulta, LocalDate> colData;

    private ObservableList<Consulta> faltasData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configura as colunas usando os métodos que você já criou na classe Consulta
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        carregarFaltas();
    }

    private void carregarFaltas() {
        faltasData.clear();
        ConsultaRepository repository = new ConsultaRepository();

        List<Consulta> todasAsConsultas = repository.loadAll();

        List<Consulta> apenasFaltas = todasAsConsultas.stream()
                .filter(c -> c.getStatusConsulta() == StatusConsulta.FALTOU || c.getStatusConsulta() == StatusConsulta.CANCELADA)
                .collect(Collectors.toList());

        faltasData.addAll(apenasFaltas);
        tableFaltas.setItems(faltasData);
    }
}

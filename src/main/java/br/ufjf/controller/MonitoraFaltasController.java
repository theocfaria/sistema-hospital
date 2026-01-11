package br.ufjf.controller;

import br.ufjf.model.Consulta;
import br.ufjf.model.Receptionist;
import br.ufjf.model.StatusConsulta;
import br.ufjf.repository.ConsultaRepository;
import br.ufjf.utils.AlertExibitor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MonitoraFaltasController implements DashboardController<Receptionist>{

    @FXML private TableView<Consulta> tableFaltas;
    @FXML private TableColumn<Consulta, String> colPaciente;
    @FXML private TableColumn<Consulta, String> colMedico;
    @FXML private TableColumn<Consulta, LocalDate> colData;
    @FXML private TableColumn<Consulta, StatusConsulta> colStatus;

    private Receptionist receptionist;
    private ObservableList<Consulta> faltasData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusConsulta"));

        carregarFaltasCancelamentos();
    }

    @FXML
    private void handleAcoes() {
        Consulta selecionada = tableFaltas.getSelectionModel().getSelectedItem();

        if (selecionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/receptionist/AcoesConsulta.fxml"));
                Parent root = loader.load();

                AcoesConsultaController controller = loader.getController();
                controller.setConsulta(selecionada);

                Stage stage = new Stage();
                stage.setTitle("Ações de Consulta - Reagendamento");
                stage.setScene(new Scene(root, 500, 400));

                stage.setMinWidth(500);
                stage.setMinHeight(400);

                stage.setResizable(false);

                stage.initModality(Modality.APPLICATION_MODAL);

                stage.showAndWait();

                carregarFaltasCancelamentos();

            } catch (IOException e) {
                e.printStackTrace();
                AlertExibitor.exibirAlerta("Erro ao abrir a tela de ações.");
            }
        } else {
            AlertExibitor.exibirAlerta("Favor selecionar uma consulta na tabela.");
        }
    }

    private void carregarFaltasCancelamentos() {
        faltasData.clear();
        ConsultaRepository repository = new ConsultaRepository();

        List<Consulta> todasAsConsultas = repository.loadAll();

        List<Consulta> apenasFaltasCancelamentos = todasAsConsultas.stream()
                .filter(c -> c.getStatusConsulta() == StatusConsulta.FALTOU || c.getStatusConsulta() == StatusConsulta.CANCELADA)
                .collect(Collectors.toList());

        faltasData.addAll(apenasFaltasCancelamentos);
        tableFaltas.setItems(faltasData);
    }

    @Override
    public void setUser(Receptionist user) {
        this.receptionist = user;
    }
}

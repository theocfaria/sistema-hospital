package br.ufjf.controller;

import br.ufjf.model.Medico;
import br.ufjf.model.Pacient;
import br.ufjf.model.Receptionist;
import br.ufjf.repository.PacientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PacienteTableController implements DashboardController<Receptionist> {

    @FXML private TableView<Pacient> tablePacients;
    @FXML private TableColumn<Pacient, String> colNome;
    @FXML private TableColumn<Pacient, String> colCpf;
    @FXML private TableColumn<Pacient, String> colPassword;
    @FXML private TableColumn<Pacient, String> colInternado;
    @FXML private Button btnDelete;
    @FXML private Button btnAdd;

    Receptionist receptionist;

    private ObservableList<Pacient> pacientData = FXCollections.observableArrayList();

    @Override
    public void setUser(Receptionist user){
        this.receptionist = user;
    }

    @FXML
    public void initialize() {

        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colInternado.setCellValueFactory(new PropertyValueFactory<>("statusInternacao"));

        tablePacients.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && tablePacients.getSelectionModel().getSelectedItem() != null){
                abrirModalEdicao(tablePacients.getSelectionModel().getSelectedItem());
            }
        });

        carregarDados();
    }

    private void carregarDados() {
        pacientData.clear();

        PacientRepository pacientRepository = new PacientRepository();

        pacientData.addAll(pacientRepository.loadAll());

        tablePacients.setItems(pacientData);
    }

    @FXML
    private void abrirModalEdicao(Pacient pacient){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/receptionist/ModalsPacient/Edit.fxml"));
            Parent root = loader.load();

            PacienteEditController controller = loader.getController();

            controller.setPaciente(pacient);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 500));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            carregarDados();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAbrirCadastro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/receptionist/ModalsPacient/Create.fxml"));
            Parent root = loader.load();

            PacienteFormController formController = loader.getController();

            formController.setLista(pacientData);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 500));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExcluirPaciente() {

        Pacient selecionado = tablePacients.getSelectionModel().getSelectedItem();

        if(selecionado != null){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Excluir o médico: " + selecionado.getName() + " ?");
            alert.setContentText("Tem certeza que deseja excluir? Essa ação não poderá ser desfeita!");

            ButtonType botaoSim = new ButtonType("Sim");
            ButtonType botaoNao = new ButtonType("Cancelar");
            alert.getButtonTypes().setAll(botaoSim, botaoNao);

            Optional<ButtonType> resultado = alert.showAndWait();

            if(resultado.isPresent() && resultado.get() == botaoSim){
                pacientData.remove(selecionado);
                PacientRepository repository = new PacientRepository();
                repository.deleteByCpf(selecionado.getCpf());
            }
        }
    }
}

package br.ufjf.controller;

import br.ufjf.model.Medico;
import br.ufjf.model.Receptionist;
import br.ufjf.repository.MedicoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MedicoTableController implements DashboardController<Receptionist> {

    @FXML private TableView<Medico> tableMedicos;
    @FXML private TableColumn<Medico, String> colNome;
    @FXML private TableColumn<Medico, String> colCpf;
    @FXML private TableColumn<Medico, String> colPassword;
    @FXML private Button btnDelete;
    @FXML private Button btnAdd;

    Receptionist receptionist;

    private ObservableList<Medico> medicoData = FXCollections.observableArrayList();

    public void setUser(Receptionist user){
        this.receptionist = user;
    }

    @FXML
    public void initialize() {

        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableMedicos.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && tableMedicos.getSelectionModel().getSelectedItem() != null){
                abrirModalEdicao(tableMedicos.getSelectionModel().getSelectedItem());
            }
        });

        carregarDados();
    }

    private void carregarDados() {
        medicoData.clear();

        MedicoRepository medicoRepository = new MedicoRepository();

        medicoData.addAll(medicoRepository.loadAll());

        tableMedicos.setItems(medicoData);
    }

    @FXML
    private void abrirModalEdicao(Medico medico){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/receptionist/ModalsMedico/Edit.fxml"));
            Parent root = loader.load();

            MedicoEditController controller = loader.getController();

            controller.setMedico(medico);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/receptionist/ModalsMedico/Create.fxml"));
            Parent root = loader.load();

            MedicoFormController formController = loader.getController();

            formController.setLista(medicoData);

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
    private void handleExcluirMedico() {

        Medico selecionado = tableMedicos.getSelectionModel().getSelectedItem();

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
                medicoData.remove(selecionado);
                MedicoRepository repository = new MedicoRepository();
                repository.deleteByCpf(selecionado.getCpf());
            }
        }
    }
}

package br.ufjf.controller;

import br.ufjf.model.Medico;
import br.ufjf.repository.MedicoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MedicoTableController {

    @FXML private TableView<Medico> tableMedicos;
    @FXML private TableColumn<Medico, String> colNome;
    @FXML private TableColumn<Medico, String> colCpf;
    @FXML private TableColumn<Medico, String> colPassword;

    private ObservableList<Medico> medicoData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        carregarDados();
    }

    private void carregarDados() {
        medicoData.clear();

        MedicoRepository medicoRepository = new MedicoRepository();

        medicoData.addAll(medicoRepository.loadAll());

        tableMedicos.setItems(medicoData);
    }

    @FXML
    private void handleNovoMedico() {
        // Por enquanto não faz nada, só evita o erro de carregamento
        System.out.println("Botão Adicionar clicado, mas sem modal ainda.");
    }

    @FXML
    private void handleExcluirMedico() {
        // Por enquanto não faz nada, só evita o erro de carregamento
        System.out.println("Botão Adicionar clicado, mas sem modal ainda.");
    }
}

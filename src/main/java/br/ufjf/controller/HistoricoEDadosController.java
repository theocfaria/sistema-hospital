package br.ufjf.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import br.ufjf.model.Pacient;
import br.ufjf.repository.PacientRepository;
import br.ufjf.model.Documento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class HistoricoEDadosController implements Initializable, DashboardController<Pacient> {

    private Pacient user;
    private ObservableList<Documento> listaHistorico = FXCollections.observableArrayList();
    private PacientRepository repo = new PacientRepository();

    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    @FXML
    private TableView<Documento> historyTable;
    @FXML
    private TableColumn<Documento, String> colData;
    @FXML
    private TableColumn<Documento, String> colDescricao;
    @FXML
    private TableColumn<Documento, String> colMedico;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("medico"));

        historyTable.setItems(listaHistorico);
        historyTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && historyTable.getSelectionModel().getSelectedItem() != null) {
                Documento doc = historyTable.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Detalhes do Registro Clínico");
                alert.setHeaderText(doc.getTipo() + " - " + doc.getData());

                String conteudo = "Médico: " + doc.getMedico() + "\n" +
                        "Informação: " + doc.getInformacao();

                if (doc.getDiasAfastamento() > 0) {
                    conteudo += "\nDias de Afastamento: " + doc.getDiasAfastamento();
                }

                alert.setContentText(conteudo);
                alert.showAndWait();
            }
        });
    }

    @Override
    public void setUser(Pacient user) {
        this.user = user;
        if (this.user != null) {
            carregarDadosInterface();
        }
    }

    private void carregarDadosInterface() {
        emailField.setText(user.getEmail());
        phoneField.setText(user.getTelefone());
        addressField.setText(user.getEndereco());

        if (user.getDocumentos() != null) {
            List<Documento> docs = new ArrayList<>(user.getDocumentos());

            docs.sort((d1, d2) -> d2.getData().compareTo(d1.getData()));

            listaHistorico.setAll(docs);
        }
    }

    @FXML
    public void salvarAlteracoes() {
        if (user == null)
            return;

        try {
            String novoEmail = emailField.getText().trim();
            String novoTelefone = phoneField.getText().trim();
            String novoEndereco = addressField.getText().trim();

            if (novoEmail.isEmpty() || novoTelefone.isEmpty()) {
                exibirAlerta("Erro", "Campos Obrigatórios", "E-mail e Telefone não podem estar vazios.",
                        Alert.AlertType.ERROR);
                return;
            }

            user.setEmail(novoEmail);
            user.setTelefone(novoTelefone);
            user.setEndereco(novoEndereco);

            repo.update(user);

            exibirAlerta("Sucesso", "Dados Atualizados", "Suas informações foram salvas com sucesso!",
                    Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            exibirAlerta("Erro", "Falha ao Salvar", "Ocorreu um erro ao atualizar os dados: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void exibirAlerta(String titulo, String cabecalho, String conteudo, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }
}

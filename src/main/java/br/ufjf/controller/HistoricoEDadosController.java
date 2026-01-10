package br.ufjf.controller;

import java.net.URL;
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
    private TableColumn<Documento, String> colTipo;
    @FXML
    private TableColumn<Documento, String> colDescricao;
    @FXML
    private TableColumn<Documento, String> colMedico;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Vinculação das colunas (Certifique-se que os nomes batem com os Getters em
        // Documento)
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("medico"));

        historyTable.setItems(listaHistorico);
    }

    @Override
    public void setUser(Pacient user) {
        this.user = user;
        if (this.user != null) {
            carregarDadosInterface();
        }
    }

    private void carregarDadosInterface() {
        // 1. O QUE FALTA: Preencher os campos de texto com os dados atuais do paciente
        emailField.setText(user.getEmail());
        phoneField.setText(user.getTelefone());
        addressField.setText(user.getEndereco()); // Assumindo que este getter existe em User/Pacient

        // 2. Preencher a tabela
        if (user.getDocumentos() != null) {
            listaHistorico.setAll(user.getDocumentos());
        }
    }

    @FXML
    public void salvarAlteracoes() {
        if (user == null)
            return;

        try {
            // 3. O QUE FALTA: Capturar todos os campos, incluindo endereço
            String novoEmail = emailField.getText().trim();
            String novoTelefone = phoneField.getText().trim();
            String novoEndereco = addressField.getText().trim();

            // Validação simples
            if (novoEmail.isEmpty() || novoTelefone.isEmpty()) {
                exibirAlerta("Erro", "Campos Obrigatórios", "E-mail e Telefone não podem estar vazios.",
                        Alert.AlertType.ERROR);
                return;
            }

            // Atualiza o objeto do usuário
            user.setEmail(novoEmail);
            user.setTelefone(novoTelefone);
            user.setEndereco(novoEndereco);

            // Persiste no banco/repositório
            repo.update(user);

            // 4. O QUE FALTA: Feedback visual de sucesso
            exibirAlerta("Sucesso", "Dados Atualizados", "Suas informações foram salvas com sucesso!",
                    Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            exibirAlerta("Erro", "Falha ao Salvar", "Ocorreu um erro ao atualizar os dados: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    // Método auxiliar para mensagens na tela
    private void exibirAlerta(String titulo, String cabecalho, String conteudo, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }
}

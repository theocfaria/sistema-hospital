package br.ufjf.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.ufjf.model.Consulta;
import br.ufjf.model.Pacient;
import br.ufjf.repository.ConsultaRepository;
import br.ufjf.repository.PacientRepository;
import br.ufjf.helpers.AlertExibitor;
import br.ufjf.helpers.TelefoneValidator;
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
    private ObservableList<Consulta> listaHistorico = FXCollections.observableArrayList();
    private ConsultaRepository repo = new ConsultaRepository();
    private PacientRepository repoPacient = new PacientRepository();

    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    @FXML
    private TableView<Consulta> historyTable;
    @FXML
    private TableColumn<Consulta, String> colData;
    @FXML
    private TableColumn<Consulta, String> colDescricao;
    @FXML
    private TableColumn<Consulta, String> colMedico;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricaoClinica"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));

        historyTable.setItems(listaHistorico);
        historyTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && historyTable.getSelectionModel().getSelectedItem() != null) {
                Consulta consulta = historyTable.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Detalhes do Registro Clínico");
                alert.setHeaderText(consulta.getData() + " - " + consulta.getHora());

                String conteudo = "Médico: " + consulta.getNomeMedico() + "\n" +
                        "Informação: " + (consulta.getDescricaoClinica() != null? consulta.getDescricaoClinica() : "Sem informações disponíveis");

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


        List<Consulta> consultas = repo.findConsultabyPaciente(user.getCpf());;

        if(consultas!=null){
            listaHistorico.setAll(consultas);
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

            if (novoEmail.isEmpty() || novoTelefone.isEmpty() || novoEndereco.isEmpty()) {
                AlertExibitor.exibirAlertaComplexo("Erro", "Campos Obrigatórios", "E-mail, Telefone  ou Endereço não podem estar vazios.",
                        Alert.AlertType.ERROR);
                return;
            }

            if(!TelefoneValidator.validaTelefone(novoTelefone)){
                AlertExibitor.exibirAlerta("Telefone inválido");
                return;
            }

            user.setEmail(novoEmail);
            String telefoneFormatado = TelefoneValidator.formatarTelefone(novoTelefone);
            user.setTelefone(telefoneFormatado);
            user.setEndereco(novoEndereco);

            repoPacient.update(user);

            phoneField.setText(telefoneFormatado);

            AlertExibitor.exibirAlertaComplexo("Sucesso", "Dados Atualizados", "Suas informações foram salvas com sucesso!",
                    Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            AlertExibitor.exibirAlertaComplexo("Erro", "Falha ao Salvar", "Ocorreu um erro ao atualizar os dados: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }
}

package br.ufjf.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.ufjf.model.Documento;
import br.ufjf.model.Pacient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AcessarDocumentosController implements Initializable, DashboardController<Pacient> {
    @FXML
    private ComboBox<String> filtroTipoDocumento;
    @FXML
    private TableView<Documento> documentosTable;
    @FXML
    private TableColumn<Documento, String> colData;
    @FXML
    private TableColumn<Documento, String> colTipo;
    @FXML
    private TableColumn<Documento, String> colMedico;
    @FXML
    private TableColumn<Documento, String> colDescricao;

    ObservableList<Documento> observableList = FXCollections.observableArrayList();

    private Pacient user;

    @Override
    public void setUser(Pacient user) {
        this.user = user;

        if (this.user != null) {
            filtraTipo();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("medico"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        filtroTipoDocumento.setItems(FXCollections.observableArrayList("Todos", "Atestados", "Receitas"));

        filtroTipoDocumento.setValue("Todos");

        documentosTable.setItems(observableList);

        filtroTipoDocumento.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                carregarDocumentos(newValue);
            }
        });
    }

    private void carregarDocumentos(String s) {
        if (user == null)
            return;

        List<Documento> listaTemporaria = null;

        if ("Todos".equals(s) || s == null) {
            listaTemporaria = user.getDocumentos();
        } else if ("Receitas".equals(s)) {
            listaTemporaria = user.getReceitas();
        } else if ("Atestados".equals(s)) {
            listaTemporaria = user.getAtestados();
        }

        if (listaTemporaria != null) {
            observableList.setAll(listaTemporaria);
        } else {
            observableList.clear();
        }
    }

    @FXML
    public void filtraTipo() {
        String filtro = filtroTipoDocumento.getValue();
        carregarDocumentos(filtro);
    }
}

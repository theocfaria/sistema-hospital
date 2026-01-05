package br.ufjf.controller;

import br.ufjf.model.User;
import br.ufjf.navigation.ChangeScreen;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardPacienteController implements DashboardController, ChangeScreen {
    @FXML
    private AnchorPane content;
    @FXML
    private Label LblUsername;
    @FXML
    private Button BtnLogout;
    @FXML
    private Button BtnConsulta;
    @FXML
    private Button BtnDocumentos;
    @FXML
    private Button BtnHistoricoEDados;
    @FXML
    private Button BtnVisita;
    private User user;

    @Override
    public void setUser(User user) {
        this.user = user;
        LblUsername.setText("Olá, " + user.getName());
    }

    @FXML
    public void agendarConsultas() {
        carregarContent(user, content, "paciente/agendarConsultas.fxml");
    }

    @FXML
    public void acessarDocumentos() {
        carregarContent(user, content, "paciente/acessarDocumentos.fxml");
    }

    @FXML
    public void historicoEDados() {
        carregarContent(user, content, "paciente/historicoEDados.fxml");
    }

    @FXML
    public void checarVisitas() {
        carregarContent(user, content, "paciente/checarVisitas.fxml");
    }

    @FXML
    public void logout() {
        trocarTela(BtnLogout, "Login.fxml");
    }
}

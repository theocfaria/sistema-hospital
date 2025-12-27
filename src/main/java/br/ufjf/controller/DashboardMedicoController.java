package br.ufjf.controller;

import br.ufjf.model.User;
import br.ufjf.navigation.ChangeScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DashboardMedicoController implements DashboardController, ChangeScreen {
    @FXML private AnchorPane content;
    @FXML private Label LblUsername;
    @FXML private Button BtnLogout;
    @FXML private Button BtnConsulta;
    @FXML private Button BtnDocumentos;
    @FXML private Button BtnPerfil;
    @FXML private Button BtnVisita;
    private User user;

    @Override
    public void setUser(User user){
        this.user=user;
        LblUsername.setText("Olá, "+user.getName());
    }

    @FXML
    public void monitorarAssiduidade(){
        carregarContent(content,"MonitorarAssiduidade.fxml");
    }

    @FXML
    public void emitirDocumentos(){
        carregarContent(content,"EmitirDocumentos.fxml");
    }

    @FXML
    public void gerenciarAgenda(){
        carregarContent(content,"GerenciarAgenda.fxml");
    }

    @FXML
    public void historicoClinico(){
        carregarContent(content,"HistoricoClinico.fxml");
    }

    @FXML
    public void logout(){
        trocarTela(BtnLogout,"Login.fxml");
    }
}

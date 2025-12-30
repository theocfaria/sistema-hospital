package br.ufjf.controller;

import br.ufjf.model.User;
import br.ufjf.navigation.ChangeScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DashboardReceptionistController implements DashboardController, ChangeScreen {

    @FXML private AnchorPane content;
    @FXML private Label LblUsername;
    @FXML private Button BtnLogout;
    @FXML private Button BtnPacientes;
    @FXML private Button BtnMedicos;
    @FXML private Button BtnVisita;
    @FXML private Button BtnDispon;
    private User user;

    @Override
    public void setUser(User user) {
        this.user = user;
        LblUsername.setText("Olá, "+user.getName());
    }

    @FXML
    public void gerenciarMedicos(){
        carregarContent(content,"GerenciarMedicos.fxml");
    }

    @FXML
    public void logout(){
        trocarTela(BtnLogout,"Login.fxml");
    }

}

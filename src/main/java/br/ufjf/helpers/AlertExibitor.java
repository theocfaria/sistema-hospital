package br.ufjf.helpers;

import javafx.scene.control.Alert;

public class AlertExibitor {

    public static void exibirAlertaComplexo(String titulo, String cabecalho, String conteudo, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }

    public static void exibirAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensagem);
        alert.show();
    }
}

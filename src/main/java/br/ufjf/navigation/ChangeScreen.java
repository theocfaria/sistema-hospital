package br.ufjf.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public interface ChangeScreen {
    default void trocarTela(Node node, String fxml){
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/"+fxml)
            );

            Parent root= loader.load();

            Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void carregarContent(AnchorPane content, String fxml){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/"+fxml));

            Parent tela = loader.load();

            content.getChildren().clear();

            AnchorPane.setTopAnchor(tela, 0.0);
            AnchorPane.setBottomAnchor(tela,0.0);
            AnchorPane.setLeftAnchor(tela, 0.0);
            AnchorPane.setRightAnchor(tela, 0.0);

            content.getChildren().add(tela);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

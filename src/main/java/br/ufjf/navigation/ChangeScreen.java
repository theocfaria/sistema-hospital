package br.ufjf.navigation;

import br.ufjf.controller.DashboardController;
import br.ufjf.model.User;
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

    default void carregarContent(User user, AnchorPane content, String fxml){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/"+fxml));

            Parent tela = loader.load();

            content.getChildren().clear();

            DashboardController controller = loader.getController();
            controller.setUser(user);

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

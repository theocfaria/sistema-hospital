package br.ufjf;

import br.ufjf.factory.Factory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Autores:
// Murilo Lacerda Dutra | 202435023
//Ryan Pinto Alvim | 202435024
//Theo Carvalho de Faria | 202435037

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/Login.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Minha View");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package edu.ijse.gdse71.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/SystemIcon.png")));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/GetStartedView.fxml"))));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



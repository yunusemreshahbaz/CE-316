package com.teamnine.ce316iae;

import com.teamnine.ce316iae.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        MainController mainController = new MainController(stage);
        mainController.loadMain();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

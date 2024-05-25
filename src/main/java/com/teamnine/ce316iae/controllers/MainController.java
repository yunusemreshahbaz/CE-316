package com.teamnine.ce316iae.controllers;

import com.teamnine.ce316iae.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    private static Stage stage;

    public MainController(Stage stage) {
        MainController.stage = stage;
    }

    public MainController() {
    }

    public void setStage(Stage stage) {
        MainController.stage = stage;
    }

    @FXML
    public void loadMain() {
        loadScene("/com/teamnine/ce316iae/main.fxml", "Integrated Assignment Environment - Main");
    }

    @FXML
    public void loadConfiguration() {
        loadScene("/com/teamnine/ce316iae/configuration.fxml", "Integrated Assignment Environment - Configuration");
    }

    @FXML
    public void loadProject() {
        loadScene("/com/teamnine/ce316iae/project.fxml", "Integrated Assignment Environment - Project");
    }


    @FXML
    public void maximizeWindow() {
        MainController.stage.setMaximized(true);
    }

    private void loadScene(String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            MainController.stage.setTitle(title);
            MainController.stage.setScene(scene);
            MainController.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the scene: " + fxmlFile);
        }
    }
}

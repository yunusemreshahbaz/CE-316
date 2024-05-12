package com.teamnine.ce316iae.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void loadConfiguration() {
        loadScene("/com/teamnine/ce316iae/configuration.fxml");
    }

    @FXML
    private void loadAssignment() {
        loadScene("/com/teamnine/ce316iae/assignment.fxml");
    }

    @FXML
    private void loadProject() {
        loadScene("/com/teamnine/ce316iae/project.fxml");
    }

    @FXML
    private void loadOutput() {
        loadScene("/com/teamnine/ce316iae/output.fxml");
    }

    @FXML
    private void loadZip() {
        loadScene("/com/teamnine/ce316iae/zip.fxml");
    }

    @FXML
    private void maximizeWindow() {
        stage.setMaximized(true);
    }

    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the scene: " + fxmlFile);
        }
    }
}

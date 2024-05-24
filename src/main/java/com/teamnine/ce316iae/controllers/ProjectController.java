package com.teamnine.ce316iae.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import com.teamnine.ce316iae.Configuration;

public class ProjectController {

    @FXML
    private TextField projectNameField;
    @FXML
    private ComboBox<Configuration> configurationComboBox;
    @FXML
    private TextField submissionsDirectoryField;
    @FXML
    private ComboBox<String> inputMethodComboBox;
    @FXML
    private TextField expectedOutputFileField;
    @FXML
    private Button createProjectButton;
    @FXML
    private ListView<String> projectListView;

    @FXML
    private void initialize() {
        configurationComboBox.setItems(Configuration.getConfigurations());
        configurationComboBox.setCellFactory(listView -> new ListCell<Configuration>() {
            @Override
            protected void updateItem(Configuration config, boolean empty) {
                super.updateItem(config, empty);
                setText(empty || config == null ? "" : config.getConfigurationName());
            }
        });

        inputMethodComboBox.setItems(FXCollections.observableArrayList("Standard Input", "Arguments"));
        projectListView.setItems(FXCollections.observableArrayList());
        createProjectButton.setOnAction(e -> createProject());
    }

    private void createProject() {
        String projectName = projectNameField.getText();
        Configuration config = configurationComboBox.getValue();
        String submissionsDirectory = submissionsDirectoryField.getText();
        String inputMethod = inputMethodComboBox.getValue();
        String expectedOutput = expectedOutputFileField.getText();
        projectListView.getItems().add(projectName + " - " + submissionsDirectory + " - " + inputMethod + " - " + expectedOutput + " (" + (config != null ? config.getConfigurationName() : "No configuration selected") + ")");
    }

    @FXML
    private void goBack() {
        new MainController().loadMain();
    }
}

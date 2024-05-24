package com.teamnine.ce316iae.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import com.teamnine.ce316iae.Configuration;
import com.teamnine.ce316iae.ConfigurationService;

public class ProjectController {

    @FXML
    private TextField projectNameField;
    @FXML
    private TextField projectDirectoryField;
    @FXML
    private ComboBox<Configuration> configurationComboBox; // Add this line
    @FXML
    private Button addProjectButton;
    @FXML
    private ListView<String> projectListView;

    @FXML
    private void initialize() {
        // Load configurations into the ComboBox
        configurationComboBox.setItems(ConfigurationService.getConfigurations());
        configurationComboBox.setCellFactory(listView -> new ListCell<Configuration>() {
            @Override
            protected void updateItem(Configuration config, boolean empty) {
                super.updateItem(config, empty);
                setText(empty || config == null ? "" : config.getConfigurationName());
            }
        });

        projectListView.setItems(FXCollections.observableArrayList());
        addProjectButton.setOnAction(e -> addProject());
    }

    private void addProject() {
        String projectName = projectNameField.getText();
        String projectDirectory = projectDirectoryField.getText();
        Configuration config = configurationComboBox.getValue();
        projectListView.getItems().add(projectName + " - " + projectDirectory + " (" + (config != null ? config.getConfigurationName() : "No configuration selected") + ")");
    }

    @FXML
    private void goBack() {
        new MainController().loadMain();
    }
}

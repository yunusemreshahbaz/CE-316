package com.teamnine.ce316iae.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import com.teamnine.ce316iae.Configuration;
import javafx.stage.FileChooser;
import java.io.File;

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

        projectListView.setItems(FXCollections.observableArrayList());
        createProjectButton.setOnAction(e -> createProject());
    }

    private void createProject() {
        String projectName = projectNameField.getText();
        Configuration config = configurationComboBox.getValue();
        String submissionsDirectory = submissionsDirectoryField.getText();
        String expectedOutput = expectedOutputFileField.getText();
        projectListView.getItems().add(projectName + " - " + submissionsDirectory +  " - " + expectedOutput + " (" + (config != null ? config.getConfigurationName() : "No configuration selected") + ")");
    }

    @FXML
    private void browseDirectory() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select ZIP File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ZIP Files", "*.zip")
        );
        File selectedZip = fileChooser.showOpenDialog(submissionsDirectoryField.getScene().getWindow());
        if (selectedZip != null) {
            submissionsDirectoryField.setText(selectedZip.getAbsolutePath());
        }
    }

    @FXML
    private void browseOutputFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Output File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File selectedFile = fileChooser.showOpenDialog(expectedOutputFileField.getScene().getWindow());
        if (selectedFile != null) {
            expectedOutputFileField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void goBack() {
        new MainController().loadMain();
    }
}

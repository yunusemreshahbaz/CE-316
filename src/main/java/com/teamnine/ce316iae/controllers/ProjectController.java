package com.teamnine.ce316iae.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import com.teamnine.ce316iae.Configuration;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.io.IOException;

public class ProjectController {

    @FXML
    private TextField projectNameField;
    @FXML
    private ComboBox<Configuration> configurationComboBox;
    @FXML
    private TextField submissionsDirectoryField;
    @FXML
    private TextField expectedOutputFileField;
    @FXML
    private Button createProjectButton;
    @FXML
    private ListView<String> listView3;

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

        createProjectButton.setOnAction(e -> createProject());
    }

    private void createProject() {
        String projectName = projectNameField.getText();
        Configuration config = configurationComboBox.getValue();
        String submissionsDirectory = submissionsDirectoryField.getText();
        String expectedOutput = expectedOutputFileField.getText();
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
            displayOutputFileContents(selectedFile);
        }
    }

    private void displayOutputFileContents(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            listView3.setItems(FXCollections.observableArrayList(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void goBack() {
        new MainController().loadMain();
    }
}

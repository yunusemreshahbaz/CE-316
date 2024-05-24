package com.teamnine.ce316iae.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ZipController {

    @FXML
    private TextField zipDirectoryField;

    @FXML
    private Label extractionStatusLabel;

    @FXML
    private Button selectZipButton;

    @FXML
    private Button extractButton;

    private com.teamnine.ce316iae.Zip zipExtractor;

    @FXML
    private void initialize() {
        zipExtractor = new com.teamnine.ce316iae.Zip();
    }

    @FXML
    private void selectZip() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Zip File");
        File selectedFile = fileChooser.showOpenDialog(selectZipButton.getScene().getWindow());

        if (selectedFile != null) {
            zipDirectoryField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void extractZip() {
        String zipPath = zipDirectoryField.getText();
        if (!zipPath.isEmpty()) {
            zipExtractor.extractZipFiles(zipPath);
            if (zipExtractor.getIsZipPresent()) {
                extractionStatusLabel.setText("Extraction successful.");
            } else {
                extractionStatusLabel.setText("No zip files found.");
            }
        } else {
            extractionStatusLabel.setText("Please select a zip file.");
        }
    }

    @FXML
    private void goBack() {
        new MainController().loadMain();
    }
}

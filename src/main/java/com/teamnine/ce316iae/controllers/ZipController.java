package com.teamnine.ce316iae.controllers;

import com.teamnine.ce316iae.Zip;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class ZipController {

    @FXML
    private TextField zipDirectoryField;
    @FXML
    private Button extractButton;

    private Zip zip;

    @FXML
    private void initialize() {
        zip = new Zip();
        extractButton.setOnAction(e -> extractZipFiles());
    }

    private void extractZipFiles() {
        zip.setZipDirectory(zipDirectoryField.getText());
        Zip.extractZipFiles(zip.getZipDirectory());
    }
}

package com.teamnine.ce316iae.controllers;

import com.teamnine.ce316iae.Output;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OutputController {

    @FXML
    private TextField expectedOutputField;
    @FXML
    private TextField actualOutputField;
    @FXML
    private Button compareButton;
    @FXML
    private Label resultLabel;

    private Output output;

    @FXML
    private void initialize() {
        output = new Output(false, "", "");
        compareButton.setOnAction(e -> compareOutputs());
    }

    private void compareOutputs() {
        boolean result = output.compareOutput(expectedOutputField.getText(), actualOutputField.getText());
        resultLabel.setText(result ? "Match" : "Does not match");
    }
}

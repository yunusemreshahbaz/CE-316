package com.teamnine.ce316iae.controllers;

import java.io.IOError;
import java.io.IOException;

import com.teamnine.ce316iae.Assignment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class AssignmentController {

    @FXML
    private TextField assignmentIDField;
    @FXML
    private TextField sourceCodeField;
    @FXML
    private TextField expectedOutputField;
    @FXML
    private Button runAssignmentButton;

    private Assignment assignment;

    @FXML
    private void initialize() {
        runAssignmentButton.setOnAction(e -> runAssignment());
    }

    private void runAssignment() {
        if (assignment == null) {
            assignment = new Assignment();
        }
        assignment.setAssignmentID(Integer.parseInt(assignmentIDField.getText()));
        assignment.setSourceCode(sourceCodeField.getText());
        assignment.runAssignment(expectedOutputField.getText());
    }

    @FXML
    private void goBack() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/teamnine/ce316iae/main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) assignmentIDField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the previous scene.");
        }
    }
}

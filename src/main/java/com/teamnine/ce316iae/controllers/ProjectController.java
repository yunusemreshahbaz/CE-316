package com.teamnine.ce316iae.controllers;

import com.teamnine.ce316iae.Project;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectController {

    @FXML
    private TextField projectNameField;
    @FXML
    private TextField projectDirectoryField;
    @FXML
    private Button addProjectButton;
    @FXML
    private ListView<String> projectListView;

    private ObservableList<String> projects = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        projectListView.setItems(projects);
        addProjectButton.setOnAction(e -> addProject());
    }

    private void addProject() {
        Project project = new Project();
        project.setProjectName(projectNameField.getText());
        project.setProjectDirectory(projectDirectoryField.getText());
        projects.add(project.getProjectName() + " - " + project.getProjectDirectory());
    }
}

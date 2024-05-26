package com.teamnine.ce316iae.controllers;

import com.teamnine.ce316iae.Configuration;
import com.teamnine.ce316iae.Project;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Arrays;

public class ConfigurationController {

    @FXML
    private TextField configurationIDField;
    @FXML
    private TextField configurationNameField;
    @FXML
    private TextField compilerPathField;
    @FXML
    private TextField languageField;
    @FXML
    private TextField runCommandField;
    @FXML
    private TextField argumentsField;
    @FXML
    private TextField configPathField;
    @FXML
    private TextField exportPathField;
    @FXML
    private Button saveConfigurationButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button importButton;

    private Configuration configuration;

    @FXML private TableView<Configuration> configurationTable;
    @FXML private TableColumn<Configuration, Integer> columnID;
    @FXML private TableColumn<Configuration, String> columnName;
    @FXML private TableColumn<Configuration, String> columnCompilerPath;
    @FXML private TableColumn<Configuration, String> columnLanguage;

    @FXML
    private void initialize() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("configurationID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("configurationName"));
        columnCompilerPath.setCellValueFactory(new PropertyValueFactory<>("compilerPath"));
        columnLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));

        configurationTable.setItems(Configuration.getConfigurations());
    }

    @FXML
    private void exportConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration");
        File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());
        if (file != null) {
            Project project = new Project(1, "Project Name", file.getParent());
            project.exportConfiguration(configuration);
        }
    }

    @FXML
    private void importConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Configuration File");
        File file = fileChooser.showOpenDialog(importButton.getScene().getWindow());
        if (file != null) {
            Project project = new Project(1, "Project Name", file.getParent());
            Configuration importedConfiguration = project.importConfiguration();
            if (importedConfiguration != null) {
                configurationIDField.setText(String.valueOf(importedConfiguration.getConfigurationID()));
                configurationNameField.setText(importedConfiguration.getConfigurationName());
                compilerPathField.setText(importedConfiguration.getCompilerPath());
                languageField.setText(importedConfiguration.getLanguage());
                runCommandField.setText(String.join(" ", importedConfiguration.getRunCommand()));
                argumentsField.setText(String.join(" ", importedConfiguration.getArguments()));
                configPathField.setText(importedConfiguration.getConfigPath());
                exportPathField.setText(importedConfiguration.getExportPath());
            }
        }
    }

    @FXML
    private void saveConfiguration() {
        try {
            int configurationID = Integer.parseInt(configurationIDField.getText().trim());
            if (configuration == null) {
                configuration = new Configuration();
            }

            configuration.setConfigurationID(configurationID);
            configuration.setConfigurationName(configurationNameField.getText().trim());
            configuration.setCompilerPath(compilerPathField.getText().trim());
            configuration.setLanguage(languageField.getText().trim());
            configuration.setRunCommand(Arrays.asList(runCommandField.getText().split("\\s+")));
            configuration.setArguments(Arrays.asList(argumentsField.getText().split("\\s+")));
            configuration.setConfigPath(configPathField.getText().trim());
            configuration.setExportPath(exportPathField.getText().trim());

            boolean isNew = Configuration.getConfigurations().stream()
                    .noneMatch(c -> c.getConfigurationID() == configuration.getConfigurationID());

            if (isNew) {
                Configuration.addConfiguration(configuration);
                configurationTable.setItems(Configuration.getConfigurations());
            }
            configurationTable.refresh();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing configuration ID: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() {
        new MainController().loadMain();
    }

}

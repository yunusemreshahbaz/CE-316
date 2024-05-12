package com.teamnine.ce316iae.controllers;

import com.teamnine.ce316iae.Configuration;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

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

    private Configuration configuration;

    @FXML
    private void initialize() {
        saveConfigurationButton.setOnAction(e -> saveConfiguration());
    }

    private void saveConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        configuration.setConfigurationID(Integer.parseInt(configurationIDField.getText()));
        configuration.setConfigurationName(configurationNameField.getText());
        configuration.setCompilerPath(compilerPathField.getText());
        configuration.setLanguage(languageField.getText());
        configuration.setRunCommand(Arrays.asList(runCommandField.getText().split("\\s+")));
        configuration.setArguments(Arrays.asList(argumentsField.getText().split("\\s+")));
        configuration.setConfigPath(configPathField.getText());
        configuration.setExportPath(exportPathField.getText());
    }
}

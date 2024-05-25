package com.teamnine.ce316iae.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import com.teamnine.ce316iae.Configuration;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.io.IOException;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    private ListView<String> listView1;
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
            extractZipFile(selectedZip);
        }
    }

    
    private void extractZipFile(File zipFile) {
        Path destDir = Paths.get(zipFile.getParent(), "extracted");
        try {
            Files.createDirectories(destDir);
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile.toPath()))) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    Path entryPath = destDir.resolve(entry.getName());
                    if (entry.isDirectory()) {
                        Files.createDirectories(entryPath);
                    } else {
                        Files.createDirectories(entryPath.getParent());
                        Files.copy(zipInputStream, entryPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    zipInputStream.closeEntry();
                }
            }
            populateListView(destDir);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error extracting ZIP file: " + e.getMessage());
        }
    }

    private void populateListView(Path destDir) {
        listView1.getItems().clear();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(destDir)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    listView1.getItems().add(entry.getFileName().toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error populating ListView: " + e.getMessage());
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

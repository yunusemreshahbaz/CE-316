package com.teamnine.ce316iae.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import com.teamnine.ce316iae.Configuration;
import com.teamnine.ce316iae.compilersAndInterpreters.JavaCompiler;
import com.teamnine.ce316iae.compilersAndInterpreters.CCompiler;
// import com.teamnine.ce316iae.compilersAndInterpreters.PythonInterpreter;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
    private ListView<String> listView2;
    @FXML
    private ListView<String> listView3;
    @FXML
    private TextArea textAreaComparison;

    private File selectedOutputFile;
    private Path extractedDir;
    private List<File> studentDirectories = new ArrayList<>();

    @FXML
    private void initialize() {
        configurationComboBox.setItems(Configuration.getConfigurations());
        configurationComboBox.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(Configuration item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getConfigurationName());
            }
        });

        configurationComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Configuration item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getConfigurationName());
            }
        });

        createProjectButton.setOnAction(e -> createProject());
        listView1.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                run();
            }
        });
    }

    private void createProject() {
        String projectName = projectNameField.getText();
        Configuration config = configurationComboBox.getValue();
        String submissionsDirectory = submissionsDirectoryField.getText();
        String expectedOutput = expectedOutputFileField.getText();

        selectedOutputFile = new File(expectedOutput);
        
        populateStudentDirectories();
        
        listView1.getItems().clear();
        for (File dir : studentDirectories) {
            listView1.getItems().add(dir.getName());
        }
    }

    private void updateComparisonResult() {
        ObservableList<String> outputList = listView2.getItems();
        ObservableList<String> expectedOutputList = listView3.getItems();

        if (outputList.equals(expectedOutputList)) {
            textAreaComparison.setText("Correct");
        } else {
            textAreaComparison.setText("Incorrect");
        }
    }

    private void populateStudentDirectories() {
        studentDirectories.clear();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(extractedDir)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    studentDirectories.add(entry.toFile());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        extractedDir = Paths.get(zipFile.getParent(), "extracted");
        try {
            Files.createDirectories(extractedDir);
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile.toPath()))) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    Path entryPath = extractedDir.resolve(entry.getName());
                    if (entry.isDirectory()) {
                        Files.createDirectories(entryPath);
                    } else {
                        Files.createDirectories(entryPath.getParent());
                        Files.copy(zipInputStream, entryPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    zipInputStream.closeEntry();
                }
            }
            populateStudentDirectories();
            listView1.getItems().clear();
            for (File dir : studentDirectories) {
                listView1.getItems().add(dir.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            updateComparisonResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void run() {

        String selectedStudent = listView1.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            return;
        }
        
        listView2.getItems().clear();
        File studentDir = studentDirectories.stream()
                .filter(dir -> dir.getName().equals(selectedStudent))
                .findFirst().orElse(null);
        
        if (studentDir == null) {
            listView2.getItems().add("No student directory found for selected student.");
            return;
        }

        Configuration config = configurationComboBox.getValue();
        if (config == null) {
            listView2.getItems().add("No configuration selected.");
            return;
        }


        String output;
        switch (config.getLanguage().toLowerCase()) {
            case "java":
                output = new JavaCompiler(studentDir).compileAndRun(studentDir, selectedOutputFile);
                break;
            case "c":
                output = new CCompiler(studentDir).compileAndRun(studentDir, selectedOutputFile);
                break;
            // case "python":
            //     output = new PythonInterpreter(studentDir).run(studentDir, selectedOutputFile);
            //     break;
            default:
                output = "Unsupported language selected.";
        }
        
        listView2.getItems().add(output);
        updateComparisonResult();
    }



    @FXML
    private void goBack() {
        new MainController().loadMain();
    }
}

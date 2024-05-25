package com.teamnine.ce316iae.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import com.teamnine.ce316iae.Configuration;
import com.teamnine.ce316iae.compilersAndInterpreters.JavaCompiler;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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

    private File selectedOutputFile;

    private List<File> studentDirectories = new ArrayList<>();

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

        selectedOutputFile = new File(expectedOutput);

        run();
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

    private void run() {
        listView2.getItems().clear();
        JavaCompiler javaCompiler = new JavaCompiler(new File("/path/to/java/projects"));
        for (File studentDir : studentDirectories) {
            String output = javaCompiler.compileAndRun(studentDir, selectedOutputFile);
            listView2.getItems().add(output);
        }
    }

    // private String compileAndRunStudentCode(File studentDir) {
    //     try {
    //         File[] javaFiles = studentDir.listFiles((dir, name) -> name.endsWith(".java"));
    //         if (javaFiles != null) {
    //             List<String> compileCommand = new ArrayList<>();
    //             compileCommand.add("javac");
    //             for (File javaFile : javaFiles) {
    //                 compileCommand.add(javaFile.getAbsolutePath());
    //             }
    //             Process compileProcess = new ProcessBuilder(compileCommand).start();
    //             compileProcess.waitFor();
    //             if (compileProcess.exitValue() == 0) {
    //                 // if compilation is successful
    //                 File mainClass = Arrays.stream(studentDir.listFiles())
    //                         .filter(file -> file.getName().endsWith(".class"))
    //                         .findFirst().orElse(null);
    //                 if (mainClass != null) {
    //                     String className = mainClass.getName().replace(".class", "");
    //                     Process runProcess = new ProcessBuilder("java", "-cp", studentDir.getAbsolutePath(), className).start();
    //                     runProcess.waitFor();
    //                     if (runProcess.exitValue() == 0) {
    //                         // If the program ran successfully, read output from selected file
    //                         List<String> lines = Files.readAllLines(selectedOutputFile.toPath(), StandardCharsets.UTF_8);
    //                         return String.join("\n", lines);
    //                     } else {
    //                         // If the program encountered an error during execution
    //                         return new String(runProcess.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
    //                     }
    //                 } else {
    //                     return "No main class found to run.";
    //                 }
    //             } else {
    //                 // If there are compilation errors
    //                 return new String(compileProcess.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
    //             }
    //         } else {
    //             return "No Java files found.";
    //         }
    //     } catch (IOException | InterruptedException e) {
    //         e.printStackTrace();
    //         return e.getMessage();
    //     }
    // }

    @FXML
    private void goBack() {
        new MainController().loadMain();
    }
}

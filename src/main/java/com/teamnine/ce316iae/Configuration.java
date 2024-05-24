package com.teamnine.ce316iae;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;
import com.teamnine.ce316iae.compilersAndInterpreters.CCompiler;
import com.teamnine.ce316iae.compilersAndInterpreters.JavaCompiler;
import java.io.File;
import java.util.Arrays;

public class Configuration implements Serializable {

    // Attributes
    private String configurationName;
    private int configurationID;
    private String compilerPath;
    private String language;
    private List<String> runCommand;
    private List<String> arguments;
    private String configPath;
    private String exportPath;


    // Static list of configurations
    private static final ObservableList<Configuration> configurations = FXCollections.observableArrayList();

    // Methods
    public Configuration(int configurationID, String configurationName, String compilerPath, String language, List<String> runCommand,
                         List<String> arguments, String configPath, String exportPath) {
        this.configurationID = configurationID;
        this.configurationName = configurationName;
        this.compilerPath = compilerPath;
        this.language = language;
        this.runCommand = runCommand;
        this.arguments = arguments;
        this.configPath = configPath;
        this.exportPath = exportPath;
    }

    public Configuration() {

    }

    public static ObservableList<Configuration> getConfigurations() {
        if (configurations.isEmpty()) {
            loadConfigurations();
        }
        return configurations;
    }

    public static void addConfiguration(Configuration configuration) {
        configurations.add(configuration);
    }

    private static void loadConfigurations() {

        File cWorkingDirectory = new File("/path/to/c/projects");
        CCompiler cCompiler = new CCompiler(cWorkingDirectory);

        File javaWorkingDirectory = new File("/path/to/java/projects");
        JavaCompiler javaCompiler = new JavaCompiler(javaWorkingDirectory);

        addConfiguration(new Configuration(1, "C Compiler Configuration", CCompiler.COMPILER_PATH, "C",
                Arrays.asList(CCompiler.RUN_COMMAND), Arrays.asList(CCompiler.ARGS), "/configs", "/exports"));

        addConfiguration(new Configuration(2, "Java Compiler Configuration", JavaCompiler.COMPILER_PATH, "Java",
                Arrays.asList(JavaCompiler.RUN_COMMAND), Arrays.asList(JavaCompiler.ARGS), "/configs", "/exports"));
    }

    public static Configuration createConfiguration(int configurationID, String configurationName, String compilerPath, String language,
                                                    List<String> runCommand, List<String> arguments,
                                                    String configPath, String exportPath) {
        return new Configuration(configurationID, configurationName, compilerPath, language, runCommand, arguments, configPath, exportPath);
    }

    public void editConfiguration(String configurationName, String compilerPath, List<String> runCommand, List<String> arguments,
                                  String configPath, String exportPath) {
        this.configurationName = configurationName;
        this.compilerPath = compilerPath;
        this.runCommand = runCommand;
        this.arguments = arguments;
        this.configPath = configPath;
        this.exportPath = exportPath;
        System.out.println("Configuration edited.");
    }

    public void removeConfiguration() {
        System.out.println("Configuration with ID " + configurationID + " removed.");

        configurationID = -1;
        compilerPath = null;
        configurationName = null;
        language = null;
        runCommand = null;
        arguments = null;
        configPath = null;
        exportPath = null;
    }

    public void importConfiguration(String filePath) {
        try {
            // Read from file
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Configuration importedConfig = (Configuration) in.readObject();
            in.close();
            fileIn.close();

            // There may also updateConfig
            this.configurationID = importedConfig.getConfigurationID();
            this.configurationName = importedConfig.getConfigurationName();
            this.compilerPath = importedConfig.getCompilerPath();
            this.language = importedConfig.getLanguage();
            this.runCommand = importedConfig.getRunCommand();
            this.arguments = importedConfig.getArguments();
            this.configPath = importedConfig.getConfigPath();
            this.exportPath = importedConfig.getExportPath();

            System.out.println("Configuration imported successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error importing configuration: " + e.getMessage());
        }
    }

    public void exportConfiguration(String filePath) {
        try {

            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();

            System.out.println("Configuration exported successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting configuration: " + e.getMessage());
        }
    }

    // Method to get configuration information
    public String ConfigInfo() {
        return "ConfigurationID: " + this.configurationID +
                "\nCompiler Path: " + this.compilerPath +
                "\nLanguage: " + this.language +
                "\nRun Command: " + String.join(" ", this.runCommand) +
                "\nArguments: " + String.join(" ", this.arguments) +
                "\nConfig Path: " + this.configPath +
                "\nExport Path: " + this.exportPath;
    }

    // Get-set Methods
    public int getConfigurationID() {
        return configurationID;
    }

    public void setConfigurationID(int configurationID) {
        this.configurationID = configurationID;
    }

    public String getCompilerPath() {
        return compilerPath;
    }

    public void setCompilerPath(String compilerPath) {
        this.compilerPath = compilerPath;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getRunCommand() {
        return runCommand;
    }

    public void setRunCommand(List<String> runCommand) {
        this.runCommand = runCommand;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> Arguments) {
        this.arguments = Arguments;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public String getExportPath() {
        return exportPath;
    }

    public void setExportPath(String exportPath) {
        this.exportPath = exportPath;
    }

    public String getConfigurationName(){
        return  configurationName;
    }

    public void setConfigurationName(String configurationName){
        this.configurationName = configurationName;
    }
}

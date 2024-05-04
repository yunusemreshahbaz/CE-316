package com.teamnine.ce316iae;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Configuration implements Serializable {

    // Attributes
    private int configurationID;
    private String compilerPath;
    private String language;
    private List<String> runCommand; // gcc -o main.exe main.c etc.
    private List<String> Arguments;  // main.exe [input list] etc.
    private String configPath;
    private String exportPath;

    public Configuration(){
        runCommand = new ArrayList<>();
    }

    public String ConfigInfo() {
        return "ConfigurationID: " + this.configurationID +
                "\nCompiler Path: " + this.compilerPath +
                "\nLanguage: " + this.language +
                "\runCommand: " + String.join(" ", this.runCommand) +
                "\nExecutable : " + this.configPath + "/" + this.exportPath;
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

    public void setRunCommmand(List<String> runCommand) {
        this.runCommand = runCommand;
    }

    public List<String> getArguments() {
        return Arguments;
    }

    public void setArguments(List<String> Arguments) {
        this.Arguments = Arguments;
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

    // Methods
    public void createConfiguration() {

    }
    
    public void editConfiguration() {
        
    }
    
    public void removeConfiguration() {
    
    }
    
    public void importConfiguration() {

    }
    
    public void exportConfiguration() {
        
    }  
   
}

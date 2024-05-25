package com.teamnine.ce316iae;

import java.io.*;
import java.util.List;

public class Project implements Serializable {

    // Attributes
    private int projectID;
    private String projectName;
    private String projectDirectory;
    private String zipFilePath;
    private String expectedOutputFilePath;

    public Project(int projectID, String projectName, String projectDirectory) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectDirectory = projectDirectory;
    }

    // Set-get methods
    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDirectory() {
        return projectDirectory;
    }

    public void setProjectDirectory(String projectDirectory) {
        this.projectDirectory = projectDirectory;
    }


    // Methods

    public void exportConfiguration(Configuration configuration) {
        if (configuration != null) {
            String filename = "exported_configuration.ser";
            String filepath = projectDirectory + File.separator + filename;
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filepath))) {
                outputStream.writeObject(configuration);
                System.out.println("Configuration exported to file: " + filepath);
            } catch (IOException e) {
                System.out.println("Error exporting configuration: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid configuration provided.");
        }
    }

    public Configuration importConfiguration() {
        Configuration configuration = null;
        String filename = "imported_configuration.ser";
        String filepath = projectDirectory + File.separator + filename;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filepath))) {
            configuration = (Configuration) inputStream.readObject();
            System.out.println("Configuration imported from file: " + filepath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error importing configuration: " + e.getMessage());
        }
        return configuration;
    }

}

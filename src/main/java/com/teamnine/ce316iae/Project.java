package com.teamnine.ce316iae;

import java.io.Serializable;

public class Project implements Serializable{

     // Attributes 
     private int projectID;
     private String projectName;
     private String projectDirectory;

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
    
    public void createAssignment(){

    }

    public void removeAssignment(){

    }

    public void importConfiguration(){

    }

    public void exportConfiguration(){

    }
}

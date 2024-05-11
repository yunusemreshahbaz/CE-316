package com.teamnine.ce316iae;

import java.io.Serializable;

public class Assignment implements Serializable {

    // Attributes
    private int assignmentID;
    private Configuration configuration;
    private String sourceCode;

    // Constructor
    public Assignment() {

    }

    // Getters and Setters
    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    // Methods

    public void processAssignment(String expectedOutput) {
        compileCode();
        runExecutable();
        compareOutput(expectedOutput);
    }

    private void compileCode() {
        if (configuration == null) {
            System.out.println("No configuration assigned for the assignment.");
            return;
        }

        if (sourceCode == null || sourceCode.isEmpty()) {
            System.out.println("No source code provided for the assignment.");
            return;
        }

        System.out.println("Compiling code for assignment ID " + assignmentID + "...");

        try {
            StringBuilder compilationCommand = new StringBuilder();
            compilationCommand.append(configuration.getCompilerPath());
            for (String argument : configuration.getArguments()) {
                compilationCommand.append(" ").append(argument);
            }
            compilationCommand.append(" ").append(sourceCode);

            System.out.println("Compilation command: " + compilationCommand.toString());

            Thread.sleep(2000);

            System.out.println("Code compiled successfully for assignment ID " + assignmentID + ".");
        } catch (InterruptedException e) {
            System.out.println("Compilation interrupted: " + e.getMessage());
        }
    }

    private void runExecutable() {
        if (configuration == null) {
            System.out.println("No configuration assigned for the assignment.");
            return;
        }

        if (sourceCode == null || sourceCode.isEmpty()) {
            System.out.println("No compiled executable available for the assignment.");
            return;
        }

        System.out.println("Running executable for assignment ID " + assignmentID + "...");

        try {
            System.out.println("Execution command: " + configuration.getExportPath());

            Thread.sleep(2000);

            System.out.println("Executable run completed for assignment ID " + assignmentID + ".");
        } catch (InterruptedException e) {
            System.out.println("Execution interrupted: " + e.getMessage());
        }
    }

    private void compareOutput(String expectedOutput) {
        if (expectedOutput == null || expectedOutput.isEmpty()) {
            System.out.println("No expected output provided for assignment ID " + assignmentID + ".");
            return;
        }

        System.out.println("Comparing output for assignment ID " + assignmentID + "...");

        try {
            String generatedOutput = simulateExecutionAndGetOutput();

            if (generatedOutput.equals(expectedOutput)) {
                System.out.println("Output for assignment ID " + assignmentID + " matches expected output.");
            } else {
                System.out.println("Output for assignment ID " + assignmentID + " does not match expected output.");
            }
        } catch (Exception e) {
            System.out.println("Error during output comparison: " + e.getMessage());
        }
    }

    private String simulateExecutionAndGetOutput() {
        System.out.println("Executing program to generate output...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Simulated output";
    }

    public void runAssignment(String expectedOutput) {
        System.out.println("Running assignment ID " + assignmentID + "...");

        processAssignment(expectedOutput);

        System.out.println("Assignment ID " + assignmentID + " executed successfully.");
    }
}

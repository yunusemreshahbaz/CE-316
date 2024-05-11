package com.teamnine.ce316iae;

import java.util.*;
import java.io.*;

public class Output implements Serializable {

    // Attributes
    private boolean compareResult;
    private String expectedOutput;
    private String actualOutput;

    // Methods

    public boolean compareOutput(String expectedOutput, String actualOutput) {

        if (expectedOutput == null || expectedOutput.isEmpty()) {
            System.out.println("No expected output provided.");
            return false;
        }

        if (actualOutput.equals(expectedOutput)) {
            compareResult = true;
            return true;
        } else {
            compareResult = false;
            return false;
        }
    }

    public void generateCompareReport(String fileName, String expectedOutput, String actualOutput) {
        try (FileWriter writer = new FileWriter(fileName)) {
            if (compareResult) {
                writer.write("Comparison report: The expected output matches the actual output.");
                writer.write("Expected output" + expectedOutput);
                writer.write("Actual output" + actualOutput);
            } else {
                writer.write("Comparison report: The expected output does not match the actual output.");
                writer.write("Expected output" + expectedOutput);
                writer.write("Actual output" + actualOutput);
            }
            System.out.println("Comparison report generated and saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the comparison report to file: " + e.getMessage());
        }
    }


    // Set-get methods
    public boolean isCompareResult() {
        return compareResult;
    }

    public void setCompareResult(boolean compareResult) {
        this.compareResult = compareResult;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }
}

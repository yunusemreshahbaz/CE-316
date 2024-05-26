package com.teamnine.ce316iae.compilersAndInterpreters;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class PythonInterpreter {
    private File workingDirectory;
    public static final String INTERPRETER_PATH = "python3";

    public PythonInterpreter(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public String run(File studentDir, File expectedOutputFile) {
        File scriptFile = getScriptFile(studentDir);
        if (scriptFile == null) {
            return "No Python script found in the student directory.";
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(INTERPRETER_PATH, scriptFile.getAbsolutePath());
            processBuilder.directory(studentDir);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<String> outputLines = reader.lines().collect(Collectors.toList());

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return "Error running Python script. Exit code: " + exitCode;
            }

            String outputString = String.join("\n", outputLines);
            return outputString;

        } catch (Exception e) {
            return "Error running Python script: " + e.getMessage();
        }
    }

    private File getScriptFile(File directory) {
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".py"));
        if (files != null && files.length > 0) {
            return files[0];
        }
        return null;
    }
    // python
}

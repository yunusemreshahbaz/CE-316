package com.teamnine.ce316iae.compilersAndInterpreters;

import com.teamnine.ce316iae.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CCompiler extends Compiler {
    public String compilerPath;
    public String runCommand; // Updated to use .exe file
    public String args;  // Updated to specify .exe for the output

    public CCompiler(File workingDirectory) {
        super(workingDirectory);

        String baseDir = null;
        try {
            baseDir = Paths.get(new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent(), "classes", "com", "teamnine", "ce316iae").toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        compilerPath = Paths.get(baseDir, "w64devkit", "bin", "gcc.exe").toString();
        runCommand = Paths.get(baseDir, "output.exe").toString();
        args = "-o " + runCommand;
    }

    public String compileAndRun(File studentDir, File selectedOutputFile) {
        try {
            File[] cFiles = studentDir.listFiles((dir, name) -> name.endsWith(".c"));
            if (cFiles != null && cFiles.length > 0) {
                List<String> compileCommand = new ArrayList<>();
                compileCommand.add(compilerPath);
                for (File cFile : cFiles) {
                    compileCommand.add(cFile.getAbsolutePath());
                }
                compileCommand.addAll(Arrays.asList(args.split(" ")));

                System.out.println("Compile command: " + String.join(" ", compileCommand)); // Debugging line

                Process compileProcess = new ProcessBuilder(compileCommand)
                        .directory(studentDir)
                        .redirectErrorStream(true)
                        .start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    StringBuilder output = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                    compileProcess.waitFor();
                    if (compileProcess.exitValue() != 0) {
                        return "Compilation error: " + output.toString();
                    }
                }

                Process runProcess = new ProcessBuilder(runCommand)
                        .directory(studentDir)
                        .redirectErrorStream(true)
                        .start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    StringBuilder output = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                    runProcess.waitFor();
                    if (runProcess.exitValue() != 0) {
                        return "Runtime error: " + output.toString();
                    }
                    List<String> lines = Files.readAllLines(selectedOutputFile.toPath(), StandardCharsets.UTF_8);
                    return String.join("\n", lines);
                }
            } else {
                return "No C files found.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error during processing: " + e.getMessage();
        }
    }
}

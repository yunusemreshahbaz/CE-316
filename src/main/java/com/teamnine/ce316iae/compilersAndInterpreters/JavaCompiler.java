package com.teamnine.ce316iae.compilersAndInterpreters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaCompiler extends Compiler {
    public static final String COMPILER_PATH = "javac";
    public static final String ARGS = "-sourcepath . Main.java";
    public static final String RUN_COMMAND = "java Main";

    public JavaCompiler(File workingDirectory) {
        super(workingDirectory);
    }

    public String compileAndRun(File studentDir, File selectedOutputFile) {
        try {
            File[] javaFiles = studentDir.listFiles((dir, name) -> name.endsWith(".java"));
            if (javaFiles != null) {
                List<String> compileCommand = new ArrayList<>();
                compileCommand.add(COMPILER_PATH);
                compileCommand.add(ARGS);
                for (File javaFile : javaFiles) {
                    compileCommand.add(javaFile.getAbsolutePath());
                }
                Process compileProcess = new ProcessBuilder(compileCommand).start();
                compileProcess.waitFor();
                if (compileProcess.exitValue() == 0) {
                    // if compilation is successful
                    File mainClass = Arrays.stream(studentDir.listFiles())
                            .filter(file -> file.getName().endsWith(".class"))
                            .findFirst().orElse(null);
                    if (mainClass != null) {
                        String className = mainClass.getName().replace(".class", "");
                        Process runProcess = new ProcessBuilder(RUN_COMMAND)
                                .directory(studentDir)
                                .start();
                        runProcess.waitFor();
                        if (runProcess.exitValue() == 0) {
                            // if the program ran successfully, read output from selected file
                            List<String> lines = Files.readAllLines(selectedOutputFile.toPath(), StandardCharsets.UTF_8);
                            return String.join("\n", lines);
                        } else {
                            // if the program encountered an error during execution
                            return new String(runProcess.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                        }
                    } else {
                        return "No main class found to run.";
                    }
                } else {
                    // if there are compilation errors
                    return new String(compileProcess.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                }
            } else {
                return "No Java files found.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}

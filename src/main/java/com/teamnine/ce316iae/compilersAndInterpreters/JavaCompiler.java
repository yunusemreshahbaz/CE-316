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
    public static final String ARGS = "-sourcepath .";
    public static final String RUN_COMMAND = "java Main";

    public JavaCompiler(File workingDirectory) {
        super(workingDirectory);
    }

    public String compileAndRun(File studentDir, File selectedOutputFile) {
        try {
            File[] javaFiles = studentDir.listFiles((dir, name) -> name.endsWith(".java"));
            if (javaFiles == null || javaFiles.length == 0) {
                return "No Java files found.";
            }

            List<String> compileCommand = new ArrayList<>();
            compileCommand.add(COMPILER_PATH);
            compileCommand.addAll(Arrays.asList(ARGS.split(" ")));
            for (File javaFile : javaFiles) {
                compileCommand.add(javaFile.getAbsolutePath());
            }

            System.out.println("Compile command: " + String.join(" ", compileCommand));

            Process compileProcess = new ProcessBuilder(compileCommand).start();
            int compileExitCode = compileProcess.waitFor();
            if (compileExitCode != 0) {
                String compileErrors = new String(compileProcess.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                return "Compilation failed:\n" + compileErrors;
            }

            File mainClass = Arrays.stream(studentDir.listFiles())
                    .filter(file -> file.getName().endsWith(".class"))
                    .findFirst().orElse(null);
            if (mainClass == null) {
                return "No main class found to run.";
            }

            String className = mainClass.getName().replace(".class", "");

            List<String> runCommand = Arrays.asList("java", className);
            System.out.println("Run command: " + String.join(" ", runCommand));

            Process runProcess = new ProcessBuilder(runCommand)
                    .directory(studentDir)
                    .start();
            int runExitCode = runProcess.waitFor();

            if (runExitCode != 0) {
                String runtimeErrors = new String(runProcess.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                return "Runtime failed:\n" + runtimeErrors;
            }

            List<String> lines = Files.readAllLines(selectedOutputFile.toPath(), StandardCharsets.UTF_8);
            return String.join("\n", lines);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Exception occurred: " + e.getMessage();
        }
    }
}

package com.teamnine.ce316iae.compilersAndInterpreters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class RustCompiler extends Compiler {
    public static final String COMPILER_PATH = "C:\\Rust\\bin\\rustc";
    public static final String ARGS = "--out-dir .";
    public static final String RUN_COMMAND = "output.exe";

    public RustCompiler(File workingDirectory) {
        super(workingDirectory);
    }

    public String compileAndRun(File studentDir, File selectedOutputFile) {
        try {
            File[] rustFiles = studentDir.listFiles((dir, name) -> name.endsWith(".rs"));
            if (rustFiles == null || rustFiles.length == 0) {
                return "No Rust files found.";
            }

            List<String> compileCommand = new ArrayList<>();
            compileCommand.add("\"" + COMPILER_PATH + "\"");
            compileCommand.add("\"" + rustFiles[0].getAbsolutePath() + "\"");
            compileCommand.addAll(List.of(ARGS.split(" ")));

            System.out.println("Compile command: " + String.join(" ", compileCommand)); // Debugging line

            ProcessBuilder builder = new ProcessBuilder(compileCommand)
                    .directory(studentDir)
                    .redirectErrorStream(true);
            Process compileProcess = builder.start();

            String output = new String(compileProcess.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            compileProcess.waitFor();

            if (compileProcess.exitValue() != 0) {
                return "Compilation error: " + output;
            }

            Process runProcess = new ProcessBuilder("\"" + studentDir.getAbsolutePath() + File.separator + RUN_COMMAND + "\"")
                    .directory(studentDir)
                    .redirectErrorStream(true)
                    .start();
            String runtimeOutput = new String(runProcess.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            runProcess.waitFor();

            if (runProcess.exitValue() != 0) {
                return "Runtime error: " + runtimeOutput;
            }

            List<String> lines = Files.readAllLines(selectedOutputFile.toPath(), StandardCharsets.UTF_8);
            return String.join("\n", lines);
        } catch (IOException | InterruptedException e) {
            return "Error during processing: " + e.getMessage();
        }
    }

}

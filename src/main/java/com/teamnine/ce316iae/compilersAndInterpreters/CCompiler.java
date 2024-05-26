package com.teamnine.ce316iae.compilersAndInterpreters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CCompiler extends Compiler {
    public static final String COMPILER_PATH = "gcc";
    public static final String ARGS = "-o output";
    public static final String RUN_COMMAND = "./output";

    public CCompiler(File workingDirectory) {
        super(workingDirectory);
    }

    public String compileAndRun(File studentDir, File selectedOutputFile) {
        try {
            File[] cFiles = studentDir.listFiles((dir, name) -> name.endsWith(".c"));
            if (cFiles != null && cFiles.length > 0) {
                List<String> compileCommand = new ArrayList<>();
                compileCommand.add(COMPILER_PATH);
                for (File cFile : cFiles) {
                    compileCommand.add(cFile.getAbsolutePath());
                }
                compileCommand.addAll(Arrays.asList(ARGS.split(" ")));
                Process compileProcess = new ProcessBuilder(compileCommand)
                        .directory(studentDir)
                        .redirectErrorStream(true)
                        .start();
                compileProcess.waitFor();
                if (compileProcess.exitValue() == 0) {
                    Process runProcess = new ProcessBuilder(RUN_COMMAND.split(" "))
                            .directory(studentDir)
                            .redirectErrorStream(true)
                            .start();
                    runProcess.waitFor();
                    if (runProcess.exitValue() == 0) {
                        List<String> lines = Files.readAllLines(selectedOutputFile.toPath(), StandardCharsets.UTF_8);
                        return String.join("\n", lines);
                    } else {
                        return new String(runProcess.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                    }
                } else {
                    return new String(compileProcess.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                }
            } else {
                return "No C files found.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}

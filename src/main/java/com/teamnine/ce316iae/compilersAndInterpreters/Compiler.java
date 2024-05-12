package com.teamnine.ce316iae.compilersAndInterpreters;

import com.teamnine.ce316iae.Output;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public abstract class Compiler {
    protected final File workingDirectory;

    public Compiler(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public Output compile(String path, String args) throws Exception {
        Process process = new ProcessBuilder()
                .command(path, args)
                .directory(workingDirectory)
                .start();
        boolean completed = process.waitFor(10, TimeUnit.SECONDS);
        if (!completed) {
            process.destroy();
            throw new RuntimeException("Compilation process timed out");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }
            return new Output(true, "", outputBuilder.toString());
        }
    }

    public Output run(String command, String args) throws Exception {
        Process process = new ProcessBuilder()
                .command(command, args)
                .directory(workingDirectory)
                .start();
        boolean completed = process.waitFor(10, TimeUnit.SECONDS);
        if (!completed) {
            process.destroy();
            throw new RuntimeException("Execution process timed out");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }
            return new Output(true, "", outputBuilder.toString());
        }
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }
}

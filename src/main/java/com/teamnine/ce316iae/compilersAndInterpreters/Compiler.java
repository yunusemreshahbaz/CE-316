package com.teamnine.ce316iae.compilersAndInterpreters;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public abstract class Compiler {
    protected final File workingDirectory;

    public Compiler(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }
}

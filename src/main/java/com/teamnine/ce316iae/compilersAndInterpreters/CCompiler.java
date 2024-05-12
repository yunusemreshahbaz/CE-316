package com.teamnine.ce316iae.compilersAndInterpreters;

import java.io.File;

public class CCompiler extends Compiler {

    public static final String COMPILER_PATH = "gcc";
    public static final String ARGS = "main.c -o main";
    public static final String RUN_COMMAND = "./main";

    public CCompiler(File workingDirectory) {
        super(workingDirectory);
    }
}

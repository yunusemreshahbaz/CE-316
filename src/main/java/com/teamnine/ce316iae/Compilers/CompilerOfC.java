package com.teamnine.ce316iae.Compilers;

import java.io.IOException;

public class CompilerOfC implements Compiler {
    @Override
    public int compile(String compilerPath, String arguments) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(compilerPath, arguments);
        Process compilationProcess = processBuilder.start();
        compilationProcess.waitFor();
        return compilationProcess.exitValue();
    }    
}

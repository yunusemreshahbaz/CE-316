package com.teamnine.ce316iae.Compilers;

import java.io.IOException;

public class CompilerOfC implements Compiler {
    @Override
    public int compile(String compilerPath, String[] arguments) throws IOException, InterruptedException {
        String[] commandarray = new String[arguments.length + 1];
        System.arraycopy(arguments, 0, commandarray, 1, arguments.length);
        commandarray[0] = compilerPath;
        System.out.println(commandarray[0] + commandarray[1]);
        Process compilationProcess = Runtime.getRuntime().exec(commandarray);
        compilationProcess.waitFor();
        return compilationProcess.exitValue();
    }    
}

package com.teamnine.ce316iae;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;
import com.teamnine.ce316iae.compilersAndInterpreters.CCompiler;
import com.teamnine.ce316iae.compilersAndInterpreters.JavaCompiler;
import java.io.File;
import java.util.Arrays;

public class ConfigurationService {
    private static final ObservableList<Configuration> configurations = FXCollections.observableArrayList();

    public static ObservableList<Configuration> getConfigurations() {
        if (configurations.isEmpty()) {
            loadConfigurations();  // Load configurations if list is empty
        }
        return configurations;
    }

    public static void addConfiguration(Configuration configuration) {
        configurations.add(configuration);
    }

    private static void loadConfigurations() {
        // Example loading logic
        File cWorkingDirectory = new File("/path/to/c/projects");
        CCompiler cCompiler = new CCompiler(cWorkingDirectory);

        File javaWorkingDirectory = new File("/path/to/java/projects");
        JavaCompiler javaCompiler = new JavaCompiler(javaWorkingDirectory);

        addConfiguration(new Configuration(1, "C Compiler Configuration", CCompiler.COMPILER_PATH, "C",
                Arrays.asList(CCompiler.RUN_COMMAND), Arrays.asList(CCompiler.ARGS), "/configs", "/exports"));

        addConfiguration(new Configuration(2, "Java Compiler Configuration", JavaCompiler.COMPILER_PATH, "Java",
                Arrays.asList(JavaCompiler.RUN_COMMAND), Arrays.asList(JavaCompiler.ARGS), "/configs", "/exports"));
    }
}

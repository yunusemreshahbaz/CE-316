package com.teamnine.ce316iae;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;

public class ConfigurationService {
    private static final ObservableList<Configuration> configurations = FXCollections.observableArrayList();

    public static ObservableList<Configuration> getConfigurations() {
        return configurations;
    }

    public static void addConfiguration(Configuration configuration) {
        configurations.add(configuration);
    }

    // Optional: Load initial configurations from a file or database
    static {
        // Load configurations when the class is loaded
        loadConfigurations();
    }

    private static void loadConfigurations() {
        // Dummy configurations for demonstration
        addConfiguration(new Configuration(1, "Default Config", "/usr/bin/gcc", "C++", Arrays.asList("./a.out"), Arrays.asList("-O2"), "/configs", "/exports"));
        // Add more configurations if needed
    }
}

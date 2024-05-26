package com.teamnine.ce316iae.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class HelpController {

    @FXML
    private TextArea helpTextArea;

    @FXML
    public void initialize() {

        helpTextArea.setText("Welcome to the Integrated Assignment Environment. This manual provides guidance on how to use the software.\n\n" +
                "Project Management:\n" +
                "- To create a new project, click on the 'Project' button and follow the prompts to specify project details and configurations.\n" +
                "- To manage existing projects, use the same 'Project' button and select the project you wish to modify from the list.\n\n" +
                "Configuration Management:\n" +
                "- To create or edit configurations, click on the 'Configuration' button. You can specify compiler settings, execution arguments, and more.\n" +
                "- Configurations can be saved, imported, or exported for easy sharing and reuse.\n\n" +
                "Execution and Results:\n" +
                "- To run a project and compile student submissions, navigate to the project details and click 'Run'.\n" +
                "- Results and comparisons against expected outputs will be displayed in the project dashboard.\n\n" +
                "For more detailed information, refer to the full documentation provided with the software installation or contact support."
);
    }

    @FXML
    private void goBack() {
        new MainController().loadMain();
    }
}

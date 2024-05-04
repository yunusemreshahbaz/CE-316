module com.teamnine.ce316iae {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.teamnine.ce316iae to javafx.fxml;
    exports com.teamnine.ce316iae;
    exports com.teamnine.ce316iae.controllers;
    opens com.teamnine.ce316iae.controllers to javafx.fxml;
}
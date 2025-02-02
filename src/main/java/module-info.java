module edu.ijse.gdse71.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires java.mail;


    opens edu.ijse.gdse71.library.controller to javafx.fxml;
    opens edu.ijse.gdse71.library.dto.tm to javafx.base;
    exports edu.ijse.gdse71.library;
}
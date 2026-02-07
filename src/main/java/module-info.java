module com.example.gui_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gui_project to javafx.fxml;
    exports com.example.gui_project;
}
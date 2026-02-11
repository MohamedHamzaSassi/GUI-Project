module org.example.teleporti {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.prefs;
    requires java.sql;
    requires mysql.connector.j;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.materialdesign2;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.swing;
    requires java.desktop;
    requires java.naming;
    requires java.management;

    opens org.example.teleporti to javafx.fxml;
    exports org.example.teleporti;
    exports org.example.teleporti.Controllers;
    opens org.example.teleporti.Controllers to javafx.fxml;
    exports org.example.teleporti.Utils;
    opens org.example.teleporti.Utils to javafx.fxml;
    exports org.example.teleporti.Services.Auth;
    opens org.example.teleporti.Services.Auth to javafx.fxml;
    exports org.example.teleporti.Services.User;
    opens org.example.teleporti.Services.User to javafx.fxml;
    exports org.example.teleporti.Services.Reservation;
    opens org.example.teleporti.Services.Reservation to javafx.fxml;
    exports org.example.teleporti.Services.Trajet;
    opens org.example.teleporti.Services.Trajet to javafx.fxml;
    exports org.example.teleporti.Services.Message;
    opens org.example.teleporti.Services.Message to javafx.fxml;
    exports org.example.teleporti.Entities;
    opens org.example.teleporti.Entities to javafx.fxml, javafx.base;
    exports org.example.teleporti.SceneControllers;
    opens org.example.teleporti.SceneControllers to javafx.fxml;
    exports org.example.teleporti.Utils.classes;
    opens org.example.teleporti.Utils.classes to javafx.fxml;
}

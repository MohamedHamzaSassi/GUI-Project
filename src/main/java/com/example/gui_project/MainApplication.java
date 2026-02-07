// MainApplication.java
package org.example.teleporti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.teleporti.Controllers.AuthController;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.IOException;
import java.util.Objects;
import java.util.prefs.Preferences;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
        String sessionToken = prefs.get("sessionToken", null);

        FXMLLoader fxmlLoader;
        if (sessionToken != null && new AuthController().validateSession(sessionToken)) {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Views/dashboard-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Views/login-view.fxml"));
        }
        Scene scene = new Scene(fxmlLoader.load());
        String css = Objects.requireNonNull(this.getClass().getResource("Views/style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("TelePorti - Eco-Tech Carpooling");
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResource("/org/example/teleporti/Images/logo.png")).toExternalForm()));
        stage.setScene(scene);
        stage.setHeight(650.0);
        stage.setWidth(900.0);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
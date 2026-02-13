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
import org.example.teleporti.Entities.User;
import org.example.teleporti.SceneControllers.DashboardViewController;
import org.example.teleporti.SceneControllers.UserViewController;
import org.example.teleporti.Services.Auth.ServiceAuth;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.IOException;
import java.util.Objects;
import java.util.prefs.Preferences;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Preferences prefs = Preferences.userNodeForPackage(ServiceAuth.class);
        String token = prefs.get("sessionToken", null);
        AuthController authController = new AuthController();
        //System.out.println("Startup token = " + token);
        //System.out.println("Token valid? = " + authController.validateSession(token));
        User currentUser = null;
        if (token != null && authController.validateSession(token)) {
            currentUser = authController.getUserByToken(token);
        } else {
            prefs.remove("sessionToken"); // remove invalid token so it doesn't loop
        }

        FXMLLoader fxmlLoader;
        if (currentUser != null) {
            if (currentUser.getType().equals("Admin")) {
                fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Views/dashboard-view.fxml"));
            } else {
                fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Views/user-view.fxml"));
            }
        } else {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Views/login-view.fxml"));
        }

        Scene scene = new Scene(fxmlLoader.load());

        if (currentUser != null) {
            Object controller = fxmlLoader.getController();
            if (controller instanceof DashboardViewController) {
                ((DashboardViewController) controller).setCurrentUser(currentUser);
            } else if (controller instanceof UserViewController) {
                ((UserViewController) controller).setCurrentUser(currentUser);
            }
        }

        String css = Objects.requireNonNull(this.getClass().getResource("Views/style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Co-Transport - Where Every Journey Is Shared.");
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResource("/org/example/teleporti/Images/logo.png")).toExternalForm()));
        stage.setScene(scene);
        stage.setHeight(780.0);
        stage.setWidth(950.0);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
}

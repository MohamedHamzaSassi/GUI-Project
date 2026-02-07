package org.example.teleporti.SceneControllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;

public class LoginViewController {

    protected final AuthController authController = new AuthController();

    @FXML
    protected Label errorText;
    @FXML
    protected TextField emailText;
    @FXML
    protected PasswordField passwordText;
    @FXML
    protected CheckBox staySignedInCheckBox;


    @FXML
    protected void onFormSubmit() {
        String email = emailText.getText();
        String password = passwordText.getText();
        Scene scene;

        try {
            User user = authController.getUserByEmailAndPassword(email, password);

            if (!authController.connection(email, password, staySignedInCheckBox.isSelected())) {
                errorText.setText("Email ou mot de passe incorrect.");
            } else {
                errorText.setText("Login successful!");
                if (user.getType().equals("Admin")) {
                    Router.goToDashboard(user, errorText);
                } else {
                    Router.goToUser(user, errorText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void goToSignup() {
        try {
            Router.goToRegister(errorText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package org.example.teleporti.SceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.EmailVerification;
import org.example.teleporti.Utils.Router;

public class SignupViewController {

    private final UserController userController = new UserController();
    private final AuthController authController = new AuthController();

    @FXML
    public TextField governeratText = new TextField("");
    @FXML
    public TextField villeText = new TextField("");
    @FXML
    private Label errorText = new Label("");
    @FXML
    private TextField ageText = new TextField("");
    @FXML
    private TextField emailText = new TextField("");
    @FXML
    private PasswordField passwordText = new PasswordField();
    @FXML
    private TextField nomText = new TextField("");
    @FXML
    private TextField prenomText = new TextField("");

    @FXML
    protected void onFormSubmit() {
        String nom = nomText.getText();
        String prenom = prenomText.getText();
        String email = emailText.getText();
        String password = passwordText.getText();
        String gouvernerat = governeratText.getText();
        String ville = villeText.getText();
        int age = Integer.parseInt(ageText.getText());

        User newUser = new User(userController.getSize() + 1, nom, prenom, age, email, password, "Client", gouvernerat, ville, "", "");

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || ageText.getText().isEmpty() || gouvernerat.isEmpty() || ville.isEmpty()) {
            errorText.setText("Please fill in all fields.");
        } else if (!EmailVerification.isValid(email)) {
            errorText.setText("Please enter a valid email address.");
        } else if (authController.inscription(newUser)) {
            goToLogin();
        } else {
            errorText.setText("Registration failed. Please try again.");
        }
    }

    @FXML
    protected void goToLogin() {
        try {
            Router.goToLogin(errorText);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
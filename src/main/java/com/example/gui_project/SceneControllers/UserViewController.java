package org.example.teleporti.SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;

import java.util.List;

public class UserViewController {

    private final AuthController authController = new AuthController();
    private final UserController userController = new UserController();

    @FXML
    protected Label nbrChauff;
    @FXML
    protected Label welcome = new Label("");
    @FXML
    protected User currentUser;
    @FXML
    protected List<User> chauffeurs;

    private int chauffeursCount = 0;

    @FXML
    public void initialize() {
        try {
            chauffeurs = userController.getAllChauffeurs();
            chauffeursCount = chauffeurs.size();
            nbrChauff.setText(chauffeursCount + " Chauffeurs valable(s)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onGoToDrivers() {
        try {
            Router.goToDrivers(currentUser, welcome, chauffeurs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogout() {
        Router.handleLogout(currentUser, welcome, authController);
    }

    public void setWelcomeMessage(String message) {
        welcome.setText("Bienvenue, " + message + "!");
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        setWelcomeMessage(currentUser.getPrenom());
    }

    @FXML
    public void onGoToProfile() {
        try {
            Router.goToProfile(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onGoToMessages() {
        try {
            Router.goToMessages(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onGoToHistory() {
        try {
            Router.goToHistory(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
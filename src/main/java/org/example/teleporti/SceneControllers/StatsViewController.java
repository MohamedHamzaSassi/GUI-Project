package org.example.teleporti.SceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;

public class StatsViewController {

    private final AuthController authController = new AuthController();

    @FXML
    private Label welcome = new Label("");

    protected User currentUser;

    @FXML
    public void initialize() {
    }

    public void setWelcomeMessage(String message) {
        welcome.setText("Bienvenue, " + message + "!");
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        setWelcomeMessage(currentUser.getPrenom());
    }

    @FXML
    public void onLogout() {
        Router.handleLogout(currentUser, welcome, authController);
    }

    public void onGoToStats() {
        try {
            Router.goToStats(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onGoToDashboard() {
        try {
            Router.goToDashboard(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onGoToProfile() {
        try {
            Router.goToProfile(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onGoToMaps() {
        try {
            Router.goToMaps(currentUser, welcome);
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

    public void onGoToUsers() {
        try {
            Router.goToUsers(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onGoToTrajets() {
        try {
            Router.goToTrajets(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onGoToReservations() {
        try {
            Router.goToReservations(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


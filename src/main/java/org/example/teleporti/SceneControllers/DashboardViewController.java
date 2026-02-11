package org.example.teleporti.SceneControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.Objects;

public class DashboardViewController {

    private final UserController userController = new UserController();
    private final AuthController authController = new AuthController();
    @FXML
    protected Label welcome = new Label("");
    @FXML
    protected PieChart usersPieChart;
    @FXML
    private User currentUser;

    @FXML
    public void initialize() {
        usersPieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Admins", userController.countByType("Admin")),
                new PieChart.Data("Clients", userController.countByType("Client")),
                new PieChart.Data("Chauffeurs", userController.countByType("Chauffeur"))
        ));
    }

    @FXML
    protected void onLogout() {
        Router.handleLogout(currentUser, welcome, authController);
    }

    public void setWelcomeMessage(String string) {
        welcome.setText("Bienvenue, " + string + "!");
    }

    public void onGoToProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Router.PROFILE_VIEW));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) welcome.getScene().getWindow();
            ProfileViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void onGoToUsers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Router.USERS_VIEW));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) welcome.getScene().getWindow();
            UsersViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onGoToStats() {
        try {
            Router.goToStats(currentUser, welcome);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @FXML
    public void setCurrentUser(User user) {
        this.currentUser = user;
        setWelcomeMessage(currentUser.getPrenom());
    }

    @FXML
    public void onGoToMaps() {
        try {
            Router.goToMaps(currentUser, welcome);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @FXML
    public void onGoToDashboard() {
        try {
            Router.goToDashboard(currentUser, welcome);
        } catch (Exception e) {
            e.getStackTrace();
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


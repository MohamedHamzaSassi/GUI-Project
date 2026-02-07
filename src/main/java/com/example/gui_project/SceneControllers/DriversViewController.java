package org.example.teleporti.SceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;

import java.util.List;

public class DriversViewController {

    private final UserController userController = new UserController();
    private final AuthController authController = new AuthController();
    @FXML
    protected Label welcome;
    @FXML
    protected TextField searchField;
    @FXML
    protected User currentUser;

    @FXML
    protected TableView<User> usersTable;

    @FXML
    protected TableColumn<User, Integer> idColumn;

    @FXML
    protected TableColumn<User, String> emailColumn;

    @FXML
    protected TableColumn<User, String> nomColumn;

    @FXML
    protected TableColumn<User, String> prenomColumn;

    @FXML
    protected TableColumn<User, String> governeratColumn;

    @FXML
    protected TableColumn<User, String> telephoneColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        governeratColumn.setCellValueFactory(new PropertyValueFactory<>("governerat"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        searchField.textProperty().addListener((_, _, newValue) -> {
            ObservableList<User> users = FXCollections.observableArrayList(userController.rechercher(newValue));
            usersTable.setItems(users);
        });
        ObservableList<User> users = FXCollections.observableArrayList(userController.afficherList());
        usersTable.setItems(users);
    }

    @FXML
    protected void onLogout() {
        Router.handleLogout(currentUser, welcome, authController);
    }

    @FXML
    protected void onRefresh() {
        Router.goToDrivers(currentUser, welcome, usersTable.getItems());
        System.out.println("Refreshed.");
    }

    public void setWelcomeMessage(String string) {
        welcome.setText("Bienvenue, " + string + "!");
    }

    public void onGotToProfile() {
        try {
            Router.goToProfile(currentUser, welcome);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void setCurrentUser(User user) {
        this.currentUser = user;
        setWelcomeMessage(currentUser.getPrenom());
    }

    @FXML
    public void setChauffeurs(List<User> chauffeurs) {
        ObservableList<User> users = FXCollections.observableArrayList(chauffeurs);
        usersTable.setItems(users);
    }

    public void onGotToHome() {
        try {
            if (currentUser.getType().equals("Admin")) {
                Router.goToDashboard(currentUser, welcome);
            } else {
                Router.goToUser(currentUser, welcome);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

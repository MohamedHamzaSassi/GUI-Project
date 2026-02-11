package org.example.teleporti.SceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;

import java.io.IOException;

public class UsersViewController {

    private final UserController userController = new UserController();
    private final AuthController authController = new AuthController();

    @FXML
    protected Label welcome;
    @FXML
    protected TextField searchField;
    @FXML
    protected User currentUser;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    public TableColumn<User, String> nomColumn;

    @FXML
    public TableColumn<User, String> prenomColumn;

    @FXML
    private TableColumn<User, String> typeColumn;

    @FXML
    private TableColumn<User, String> governeratColumn;

    @FXML
    private TableColumn<User, String> telephoneColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        governeratColumn.setCellValueFactory(new PropertyValueFactory<>("governerat"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        ObservableList<User> users = FXCollections.observableArrayList(userController.afficherList());
        usersTable.setItems(users);
        searchField.textProperty().addListener((v1, v2, newValue) -> {
            ObservableList<User> usersList = FXCollections.observableArrayList(users.stream().filter(user -> {
                String value = newValue.toLowerCase();
                return user.getNom().toLowerCase().contains(value.toLowerCase()) ||
                        user.getPrenom().toLowerCase().contains(value.toLowerCase()) ||
                        user.getEmail().toLowerCase().contains(value.toLowerCase()) ||
                        user.getGovernerat().toLowerCase().contains(value.toLowerCase()) ||
                        user.getTelephone().toLowerCase().contains(value.toLowerCase());
            }).toList());
            usersTable.setItems(usersList);
        });
    }

    @FXML
    protected void onLogout() {
        Router.handleLogout(currentUser, welcome, authController);
    }

    @FXML
    protected void onRefresh() {
        Router.goToUsers(currentUser, welcome);
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

    public void onGoToUsers() {
        try {
            Router.goToUsers(currentUser, welcome);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onGoToStats() {
        try {
            Router.goToStats(currentUser, welcome);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void setCurrentUser(User user) {
        this.currentUser = user;
        setWelcomeMessage(currentUser.getPrenom());
    }

    public void onGoToMaps() {
        try {
            Router.goToMaps(currentUser, welcome);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void onGoToDashboard() {
        try {
            Router.goToDashboard(currentUser, welcome);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
    private void openAddModal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Router.ADD_MODAL_VIEW));
            Scene scene = new Scene(loader.load());
            AddModalController controller = loader.getController();
            controller.setUser();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add User");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openEditModal() {
        User user = usersTable.getSelectionModel().getSelectedItem();

        if (user == null) {
            Router.openErrorMessageModal("Merci de selectionner un utilisateur à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Router.EDIT_USER_MODAL_VIEW));
            Scene scene = new Scene(loader.load());
            EditUserModalController controller = loader.getController();
            controller.setUser(user);
            controller.setCurrentUser(currentUser);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edit User");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDeleteModal() {
        User user = usersTable.getSelectionModel().getSelectedItem();

        if (user == null) {
            Router.openErrorMessageModal("Merci de selectionner un utilisateur à supprimer.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Router.DELETE_MODAL_VIEW));
            Scene scene = new Scene(loader.load());
            DeleteModalController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Delete User");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToProfile() {
        try {
            Router.goToProfile(currentUser, welcome);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onGoToTrajets() {
        try {
            Router.goToTrajets(currentUser, welcome);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onGoToReservations() {
        try {
            Router.goToReservations(currentUser, welcome);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


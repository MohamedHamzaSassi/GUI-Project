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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.TrajetController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.Trajet;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;

import java.io.IOException;

public class TrajetsViewController {

    private final TrajetController trajetController = new TrajetController();
    private final UserController userController = new UserController();
    private final AuthController authController = new AuthController();

    @FXML
    public TextField searchField;

    @FXML
    public TableColumn<Trajet, Integer> idColumn;
    @FXML
    public TableColumn<Trajet, String> conducteurColumn;
    @FXML
    public TableColumn<Trajet, String> pointDepartColumn;
    @FXML
    public TableColumn<Trajet, String> destinationColumn;
    @FXML
    public TableColumn<Trajet, String> vehicleTypeColumn;
    @FXML
    public TableColumn<Trajet, String> dateHeureColumn;
    @FXML
    public TableColumn<Trajet, String> placesDisponiblesColumn;
    @FXML
    public TableColumn<Trajet, String> co2EconomiseColumn;
    @FXML
    public TableColumn<Trajet, String> prixColumn;

    private User currentUser;

    @FXML
    protected Label welcome;

    @FXML
    protected TableView<Trajet> trajetsTable;

    @FXML
    private Trajet selectedTrajet;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        conducteurColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("conducteurId"));
        pointDepartColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("pointDepart"));
        destinationColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("destination"));
        vehicleTypeColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("vehicleType"));
        dateHeureColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("dateHeure"));
        placesDisponiblesColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("placesDisponibles"));
        co2EconomiseColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("co2Economise"));
        prixColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("prix"));
        ObservableList<Trajet> trajets = trajetController.getAllTrajets();
        trajetsTable.setItems(trajets);
        searchField.textProperty().addListener((v1, v2, newValue) -> {
            ObservableList<Trajet> trajetsList = FXCollections.observableArrayList(trajets.stream().filter(trajet -> {
                String value = newValue.toLowerCase();
                return String.valueOf(trajet.getConducteurId()).toLowerCase().contains(value.toLowerCase()) ||
                        trajet.getPointDepart().toLowerCase().contains(value.toLowerCase()) ||
                        trajet.getDestination().toLowerCase().contains(value.toLowerCase()) ||
                        String.valueOf(trajet.getDateHeure()).toLowerCase().contains(value.toLowerCase()) ||
                        String.valueOf(trajet.getPlacesDisponibles()).toLowerCase().contains(value.toLowerCase()) ||
                        String.valueOf(trajet.getCo2Economise()).toLowerCase().contains(value.toLowerCase()) ||
                        String.valueOf(trajet.getPrix()).toLowerCase().contains(value.toLowerCase());
            }).toList());
            
            trajetsTable.setItems(trajetsList);
            
        });
    }
    
    
    @FXML
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        setWelcomeMessage(currentUser.getPrenom());
    }

    @FXML
    public void setWelcomeMessage(String message) {
        welcome.setText("Bienvenue, " + message + "!");
    }

    @FXML
    public void onLogout() {
        Router.handleLogout(currentUser, welcome, authController);
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
    public void onRefresh() {
        try {
            Router.goToTrajets(currentUser, welcome);
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
            controller.setTrajet();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajout Trajet");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openEditModal() {
        selectedTrajet = trajetsTable.getSelectionModel().getSelectedItem();

        if (selectedTrajet == null) {
            Router.openErrorMessageModal("Merci de selectionner un trajet à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Router.EDIT_TRAJET_MODAL_VIEW));
            Scene scene = new Scene(loader.load());
            EditTrajetModalController controller = loader.getController();
            controller.setTrajet(selectedTrajet);
            controller.setCurrentUser(currentUser);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Trajet");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDeleteModal() {
        selectedTrajet = trajetsTable.getSelectionModel().getSelectedItem();

        if (selectedTrajet == null) {
            Router.openErrorMessageModal("Merci de selectionner un trajet à supprimer.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Router.DELETE_MODAL_VIEW));
            Scene scene = new Scene(loader.load());
            DeleteModalController controller = loader.getController();
            controller.setTrajet(selectedTrajet);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Supprimer Trajet");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


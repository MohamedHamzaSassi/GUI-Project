package org.example.teleporti.SceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.teleporti.Controllers.ReservationController;
import org.example.teleporti.Controllers.TrajetController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.Reservation;
import org.example.teleporti.Entities.Trajet;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Constants;

public class EditReservationModalController {

    private final UserController userController = new UserController();
    private final TrajetController trajetController = new TrajetController();
    private final ReservationController reservationController = new ReservationController();


    @FXML
    protected MenuButton trajetField = new MenuButton();
    @FXML
    protected MenuButton passagerField = new MenuButton();
    @FXML
    protected MenuButton statusField = new MenuButton();
    @FXML
    protected MenuButton conducteurs = new MenuButton();
    @FXML
    private VBox reservationForm = new VBox();

    @FXML
    protected User selectedClient = null;
    @FXML
    protected User currentUser = null;

    protected Trajet trajet = null;

    @FXML
    protected Reservation selectedReservation = null;


    @FXML
    public void initialize() {
    }

    @FXML
    void setReservation(Reservation selectedReservation) {
        System.out.println("selectedReservation = " + selectedReservation);
        if (selectedReservation != null) {
            this.selectedReservation = selectedReservation;
            selectedClient = userController.getUserById(selectedReservation.getPassagerId());
            reservationForm.setVisible(true);
            trajet = trajetController.getTrajetById(selectedReservation.getTrajetId());
            trajetField.getItems().addAll(trajetController.getAllTrajets().stream().map(newTrajet -> {
                MenuItem item = new MenuItem(newTrajet.getPointDepart() + " -> " + newTrajet.getDestination());
                item.setOnAction(unused -> {
                    trajetField.setText(newTrajet.getPointDepart() + " -> " + newTrajet.getDestination());
                    trajet = newTrajet;
                });
                return item;
            }).toArray(MenuItem[]::new));
            passagerField.getItems().addAll(userController.getAllClients().stream().map(user -> {
                MenuItem item = new MenuItem(user.getPrenom() + " " + user.getNom());
                item.setOnAction(unused -> {
                    passagerField.setText(user.getPrenom() + " " + user.getNom());
                    selectedClient = user;
                });
                return item;
            }).toArray(MenuItem[]::new));
            statusField.getItems().addAll(Constants.statuses.stream().map(status -> {
                MenuItem item = new MenuItem(status);
                item.setOnAction(unused -> statusField.setText(status));
                return item;
            }).toArray(MenuItem[]::new));
            if (selectedClient != null) {
                passagerField.setText(selectedClient.getPrenom() + " " + selectedClient.getNom());
            } else {
                passagerField.setText("Passager inconnu (ID: " + selectedReservation.getPassagerId() + ")");
            }
            if (trajet != null) {
                trajetField.setText(trajet.getPointDepart() + " -> " + trajet.getDestination());
            } else {
                trajetField.setText("Trajet inconnu (ID: " + selectedReservation.getTrajetId() + ")");
            }
            statusField.setText(selectedReservation.getStatus());
        } else {
            trajetField.getItems().clear();
            passagerField.getItems().clear();
            statusField.getItems().clear();
        }
    }

    @FXML
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @FXML
    public void onSave() {
        if (selectedReservation != null) {
            if (trajet != null) {
                selectedReservation.setTrajetId(trajet.getId());
            }
            if (selectedClient != null) {
                selectedReservation.setPassagerId(selectedClient.getId());
            }
            selectedReservation.setStatus(statusField.getText());
            reservationController.modifier(selectedReservation);
        }
        closeModal();
    }

    @FXML
    public void onCancel() {
        closeModal();
    }

    private void closeModal() {
        Stage stage = (Stage) reservationForm.getScene().getWindow();
        stage.close();
    }
}


package org.example.teleporti.SceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.teleporti.Controllers.ReservationController;
import org.example.teleporti.Controllers.TrajetController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.Reservation;
import org.example.teleporti.Entities.Trajet;
import org.example.teleporti.Entities.User;

public class DeleteModalController {

    @FXML
    public Label label;
    @FXML
    protected User user;
    @FXML
    protected Trajet trajet;
    @FXML
    protected Reservation reservation;

    private final UserController userController = new UserController();
    private final TrajetController trajetController = new TrajetController();
    private final ReservationController reservationController = new ReservationController();

    @FXML
    public void initialize() {
        if (user != null) {
            setUser(user);
        } else if (trajet != null) {
            setTrajet(trajet);
        } else if (reservation != null) {
            setReservation(reservation);
        }
    }

    void setReservation(Reservation reservation) {
        this.reservation = reservation;
        User passager = userController.getUserById(reservation.getPassagerId());
        if (passager != null) {
            label.setText("Voulez-vous vraiment supprimer la réservation de " + passager.getPrenom() + " ?");
        } else {
            label.setText("Voulez-vous vraiment supprimer cette réservation (Passager ID: " + reservation.getPassagerId() + ") ?");
        }
    }

    @FXML
    public void setUser(User user) {
        this.user = user;
        label.setText("Voulez-vous vraiment supprimer l'utilisateur " + user.getPrenom() + " ?");
    }

    public void onYes() {
        if (user != null) {
            userController.supprimer(user);
        } else if (trajet != null) {
            trajetController.supprimer(trajet);
        } else if (reservation != null) {
            reservationController.supprimer(reservation);
        }
        closeModal();
    }

    public void onNo() {
        closeModal();
    }

    private void closeModal() {
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
    }

    public void setTrajet(Trajet trajet) {
        label.setText("Voulez-vous vraiment supprimer le trajet " + trajet.getPointDepart() + " - " + trajet.getDestination() + " ?");
        this.trajet = trajet;
    }
}


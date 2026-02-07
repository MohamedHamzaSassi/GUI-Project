package org.example.teleporti.SceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.teleporti.Controllers.ReservationController;
import org.example.teleporti.Controllers.TrajetController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.Reservation;
import org.example.teleporti.Entities.Trajet;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Constants;

public class AddModalController {

    private final UserController userController = new UserController();
    private final ReservationController reservationController = new ReservationController();
    private final TrajetController trajetController = new TrajetController();

    // Variable de la form du reservation
    @FXML
    protected MenuButton trajetField;
    @FXML
    protected MenuButton passagerField;
    @FXML
    protected MenuButton statusField;

    @FXML
    private VBox addUserForm = new VBox();
    @FXML
    private VBox addTrajetForm = new VBox();
    @FXML
    private VBox addReservationForm = new VBox();

    // Variable de la form du trajet
    @FXML
    protected MenuButton conducteurField;
    @FXML
    protected MenuButton pointDepartField;
    @FXML
    protected MenuButton destinationField;
    @FXML
    protected TextField placesDisponiblesField;
    @FXML
    protected TextField co2EconomiseField;
    @FXML
    protected TextField prixField;

    // Variable de la form du user
    @FXML
    private TextField prenomField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField ageField;
    @FXML
    private MenuButton governeratField;
    @FXML
    private TextField villeField;
    @FXML
    private TextField telephoneField;

    @FXML
    private User user = null;
    @FXML
    private Trajet trajet = null;
    @FXML
    private Reservation reservation = null;

    @FXML
    private boolean isUserForm;
    @FXML
    private boolean isTrajetForm;
    @FXML
    private boolean isReservationForm;

    @FXML
    public void initialize() {
        isUserForm = false;
        isTrajetForm = false;
        isReservationForm = false;
        addUserForm.setVisible(false);
        addTrajetForm.setVisible(false);
        addReservationForm.setVisible(false);
    }

    @FXML
    public void setUser() {
        isUserForm = true;
        isTrajetForm = false;
        isReservationForm = false;
        addUserForm.setVisible(true);
        addTrajetForm.setVisible(false);
        addReservationForm.setVisible(false);
        user = User.empty();
        prenomField.setText("");
        nomField.setText("");
        emailField.setText("");
        passwordField.setText("");
        ageField.setText("");
        governeratField.getItems().addAll(Constants.locations.stream().map(location -> {
            MenuItem item = new MenuItem(location.getName());
            item.setOnAction(_ -> governeratField.setText(location.getName()));
            return item;
        }).toArray(MenuItem[]::new));
        villeField.setText("");
        telephoneField.setText("");
    }

    @FXML
    public void setTrajet() {
        isTrajetForm = true;
        isReservationForm = false;
        isUserForm = false;
        addUserForm.setVisible(false);
        addTrajetForm.setVisible(true);
        addReservationForm.setVisible(false);
        trajet = Trajet.empty();
        conducteurField.getItems().addAll(userController.getAllChauffeurs().stream().map(conducteur -> {
            MenuItem item = new MenuItem(conducteur.getPrenom() + " " + conducteur.getNom());
            item.setOnAction(_ -> conducteurField.setText(conducteur.getPrenom() + " " + conducteur.getNom()));
            return item;
        }).toArray(MenuItem[]::new));
        pointDepartField.getItems().addAll(Constants.locations.stream().map(location -> {
            MenuItem item = new MenuItem(location.getName());
            item.setOnAction(_ -> pointDepartField.setText(location.getName()));
            return item;
        }).toArray(MenuItem[]::new));
        destinationField.getItems().addAll(Constants.locations.stream().map(location -> {
            MenuItem item = new MenuItem(location.getName());
            item.setOnAction(_ -> destinationField.setText(location.getName()));
            return item;
        }).toArray(MenuItem[]::new));
        placesDisponiblesField.setText("");
        co2EconomiseField.setText("");
        prixField.setText("");
    }

    public void setReservation() {
        isReservationForm = true;
        isTrajetForm = false;
        isUserForm = false;
        addUserForm.setVisible(false);
        addTrajetForm.setVisible(false);
        addReservationForm.setVisible(true);
        reservation = Reservation.empty();
        trajetField.getItems().addAll(trajetController.getAllTrajets().stream().map(vtrajet -> {
            MenuItem item = new MenuItem(vtrajet.getPointDepart() + " -> " + vtrajet.getDestination());
            item.setOnAction(_ -> trajetField.setText(vtrajet.getPointDepart() + " -> " + vtrajet.getDestination()));
            return item;
        }).toArray(MenuItem[]::new));
        passagerField.getItems().addAll(userController.getAllClients().stream().map(passager -> {
            MenuItem item = new MenuItem(passager.getPrenom() + " " + passager.getNom());
            item.setOnAction(_ -> passagerField.setText(passager.getPrenom() + " " + passager.getNom()));
            return item;
        }).toArray(MenuItem[]::new));
        statusField.getItems().addAll(Constants.statuses.stream().map(status -> {
            MenuItem item = new MenuItem(status);
            item.setOnAction(_ -> statusField.setText(status));
            return item;
        }).toArray(MenuItem[]::new));
        trajetField.setText("");
        passagerField.setText("");
        statusField.setText("");
    }

    @FXML
    public void onSave() {
        try {
            if (isUserForm) {
                user.setPrenom(prenomField.getText());
                user.setNom(nomField.getText());
                user.setEmail(emailField.getText());
                user.setMotDePasse(passwordField.getText());
                user.setAge(Integer.parseInt(ageField.getText()));
                user.setGovernerat(governeratField.getText());
                user.setVille(villeField.getText());
                user.setTelephone(telephoneField.getText());
                userController.ajout(user);
            } else if (isTrajetForm) {
                String conducteurText = conducteurField.getText();
                System.out.println("Conducteur Field Text: " + conducteurText);
                trajet.setConducteurId(userController.getAllChauffeurs().stream().filter(conducteur -> {
                    String fullName = conducteur.getPrenom() + " " + conducteur.getNom();
                    return fullName.equals(conducteurText);
                }).findFirst().map(User::getId).orElseThrow(() -> new IllegalArgumentException("Conducteur not found")));
                trajet.setPointDepart(pointDepartField.getText());
                trajet.setDestination(destinationField.getText());
                trajet.setPlacesDisponibles(Integer.parseInt(placesDisponiblesField.getText()));
                trajet.setCo2Economise(Integer.parseInt(co2EconomiseField.getText()));
                trajet.setPrix(Integer.parseInt(prixField.getText()));
                trajetController.ajout(trajet);
            } else if (isReservationForm) {
                reservation.setTrajetId(trajetController.getAllTrajets().stream().filter(vtrajet -> {
                    String trajetDescription = vtrajet.getPointDepart() + " -> " + vtrajet.getDestination();
                    return trajetDescription.equals(trajetField.getText());
                }).findFirst().map(Trajet::getId).orElseThrow(() -> new IllegalArgumentException("Trajet not found")));

                String passagerText = passagerField.getText();
                reservation.setPassagerId(userController.getAllClients().stream().filter(passager -> {
                    String fullName = passager.getPrenom() + " " + passager.getNom();
                    return fullName.equals(passagerText);
                }).findFirst().map(User::getId).orElseThrow(() -> new IllegalArgumentException("Passager not found")));

                System.out.println(reservation.getPassagerId());
                reservation.setStatus(statusField.getText());
                reservationController.ajout(reservation);
            }
            closeModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCancel() {
        closeModal();
    }

    private void closeModal() {
        Stage stage = (Stage) prenomField.getScene().getWindow();
        stage.close();
    }
}

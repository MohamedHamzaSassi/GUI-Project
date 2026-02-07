package org.example.teleporti.SceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.teleporti.Controllers.TrajetController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.Trajet;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Constants;

public class EditTrajetModalController {

    private final UserController userController = new UserController();
    private final TrajetController trajetController = new TrajetController();

    @FXML
    protected MenuButton conducteurs = new MenuButton();
    @FXML
    protected MenuButton pointDepart = new MenuButton();
    @FXML
    protected MenuButton destination = new MenuButton();
    @FXML
    protected DatePicker dateHeure = new DatePicker();
    @FXML
    protected TextField placesDisponibles = new TextField();
    @FXML
    protected TextField co2Economise = new TextField();
    @FXML
    protected TextField prix = new TextField();

    @FXML
    private VBox trajetForm = new VBox();

    @FXML
    protected User user = null;
    @FXML
    protected User selectedConducteur = null;
    @FXML
    protected User currentUser = null;

    @FXML
    protected Trajet selectedTrajet = null;
    protected Trajet trajet = null;

    @FXML
    public void initialize() {
    }

    @FXML
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @FXML
    public void setTrajet(Trajet trajet) {
        if (trajet != null) {
            this.selectedTrajet = trajet;
            trajetForm.setVisible(true);
            selectedConducteur = userController.getUserById(trajet.getConducteurId());
            conducteurs.getItems().addAll(userController.getAllChauffeurs().stream().map(user -> {
                MenuItem item = new MenuItem(user.getPrenom() + " " + user.getNom());
                item.setOnAction(_ -> {
                    conducteurs.setText(user.getPrenom() + " " + user.getNom());
                    selectedConducteur = user;
                });
                return item;
            }).toArray(MenuItem[]::new));
            pointDepart.getItems().addAll(Constants.locations.stream().map(location -> {
                MenuItem item = new MenuItem(location.getName());
                item.setOnAction(_ -> pointDepart.setText(location.getName()));
                return item;
            }).toArray(MenuItem[]::new));
            destination.getItems().addAll(Constants.locations.stream().map(location -> {
                MenuItem item = new MenuItem(location.getName());
                item.setOnAction(_ -> destination.setText(location.getName()));
                return item;
            }).toArray(MenuItem[]::new));
            User conducteur = userController.getUserById(trajet.getConducteurId());
            conducteurs.setText(conducteur.getPrenom() + " " + conducteur.getNom());
            pointDepart.setText(trajet.getPointDepart());
            destination.setText(trajet.getDestination());
            dateHeure.setValue(trajet.getDateHeure().toLocalDate());
            placesDisponibles.setText(String.valueOf(trajet.getPlacesDisponibles()));
            co2Economise.setText(String.valueOf(trajet.getCo2Economise()));
            prix.setText(String.valueOf(trajet.getPrix()));
        } else {
            conducteurs.getItems().clear();
            pointDepart.getItems().clear();
            destination.getItems().clear();
        }
    }

    @FXML
    public void onSave() {
        if (selectedTrajet != null) {
            selectedTrajet.setConducteurId(selectedConducteur.getId());
            selectedTrajet.setPointDepart(pointDepart.getText());
            selectedTrajet.setDestination(destination.getText());
            selectedTrajet.setDateHeure(java.sql.Date.valueOf(dateHeure.getValue()));
            selectedTrajet.setPlacesDisponibles(Integer.parseInt(placesDisponibles.getText()));
            selectedTrajet.setCo2Economise(Float.parseFloat(co2Economise.getText()));
            selectedTrajet.setPrix(Float.parseFloat(prix.getText()));
            trajetController.modifier(selectedTrajet);
        }
        closeModal();
    }

    @FXML
    public void onCancel() {
        closeModal();
    }

    private void closeModal() {
        Stage stage = (Stage) trajetForm.getScene().getWindow();
        stage.close();
    }
}

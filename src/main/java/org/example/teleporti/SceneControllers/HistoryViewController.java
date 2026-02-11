package org.example.teleporti.SceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.TrajetController;
import org.example.teleporti.Entities.Trajet;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;

import java.sql.Date;

public class HistoryViewController {

    private final TrajetController trajetController = new TrajetController();
    private final AuthController authController = new AuthController();

    @FXML
    protected TableColumn<Trajet, Integer> idColumn;
    @FXML
    protected TableColumn<Trajet, Date> dateHeureColumn;
    @FXML
    protected TableColumn<Trajet, String> pointDepartColumn;
    @FXML
    protected TableColumn<Trajet, String> destinationColumn;
    @FXML
    protected TableColumn<Trajet, Float> co2EconomiseColumn;
    @FXML
    protected TableView<Trajet> historyTable;
    @FXML
    protected Label welcome = new Label("");

    protected User currentUser = null;

    public void setCurrentUser(User user) {
        this.currentUser = user;
        setWelcomeMessage(user.getPrenom());
    }

    public void setWelcomeMessage(String message) {
        welcome.setText("Bienvenue, " + message + "!");
    }

    @FXML
    public void initialize() {
        if (currentUser == null) {
            throw new IllegalStateException("Current user is not set");
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateHeureColumn.setCellValueFactory(new PropertyValueFactory<>("dateHeure"));
        pointDepartColumn.setCellValueFactory(new PropertyValueFactory<>("pointDepart"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        co2EconomiseColumn.setCellValueFactory(new PropertyValueFactory<>("co2Economise"));
        ObservableList<Trajet> trajets = FXCollections.observableArrayList(trajetController.getTrajetsByUserId(currentUser.getId()));
        historyTable.setItems(trajets);
        if (trajets.isEmpty()) {
            historyTable.setPlaceholder(new Label("No history found"));
        }
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
    public void onLogout() {
        Router.handleLogout(currentUser, welcome, authController);
    }

    @FXML
    public void onGoToHome() {
        try {
            Router.goToUser(currentUser, welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

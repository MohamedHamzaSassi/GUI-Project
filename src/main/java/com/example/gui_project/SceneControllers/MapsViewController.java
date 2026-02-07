package org.example.teleporti.SceneControllers;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.controlsfx.control.WorldMapView;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.TrajetController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Constants;
import org.example.teleporti.Utils.Router;

import java.util.List;

public class MapsViewController {

    private final AuthController authController = new AuthController();
    private final UserController userController = new UserController();
    private final TrajetController trajetController = new TrajetController();

    @FXML
    private VBox co2EmissionCountries;

    @FXML
    private VBox rideCostCountries;

    @FXML
    private WorldMapView worldMapView;

    @FXML
    private Label welcome = new Label("");

    protected User currentUser;

    private final ObservableList<WorldMapView.Location> locations = new ObservableListBase<>() {
        @Override
        public WorldMapView.Location get(int index) {
            String governerat = userController.getAllGovernerats().get(index);
            return Constants.locations.stream()
                    .filter(location -> location.getName().contains(governerat))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public int size() {
            return userController.getAllGovernerats().size();
        }
    };

    private final ObservableList<WorldMapView.Country> countries = new ObservableListBase<>() {
        @Override
        public WorldMapView.Country get(int index) {
            return WorldMapView.Country.TN;
        }

        @Override
        public int size() {
            return 1;
        }
    };

    @FXML
    public void initialize() {
        worldMapView.getLocations().addAll(locations);
        worldMapView.locationViewFactoryProperty().set(
                _ -> {
                    Circle circle = new Circle(1.5);
                    circle.setFill(Color.RED);
                    return circle;
                }
        );
        worldMapView.setShowLocations(true);
        worldMapView.setCountries(countries);
        worldMapView.countryViewFactoryProperty().set(
                (country) -> {
                    WorldMapView.CountryView countryView = new WorldMapView.CountryView(country);
                    countryView.setOpacity(0.5);
                    return countryView;
                }
        );
        co2EmissionCountries.getChildren().addAll(countAvgCO2EmissionByGovernerat());
        rideCostCountries.getChildren().addAll(countAvgRideCostByGovernerat());
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

    public void onGoToReservations() {
        try {
            Router.goToReservations(currentUser, welcome);
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

    @FXML
    List<Label> countAvgCO2EmissionByGovernerat() {
        return userController.getAllGovernerats().stream().map(governerat -> {
            Label label = new Label();
            label.getStyleClass().add("subtitle");
            label.setText("- " + governerat + ": " + trajetController.countAvgCO2EmissionByGovernerat(governerat));
            return label;
        }).toList();
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
    List<Label> countAvgRideCostByGovernerat() {
        return userController.getAllGovernerats().stream().map(governerat -> {
            Label label = new Label();
            label.getStyleClass().add("subtitle");
            label.setText("- " + governerat + ": " + trajetController.countAvgRideCostByGovernerat(governerat) + "DT");
            return label;
        }).toList();
    }
}

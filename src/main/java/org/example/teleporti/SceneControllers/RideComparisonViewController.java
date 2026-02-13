package org.example.teleporti.SceneControllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Controllers.TrajetController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.Trajet;
import org.example.teleporti.Entities.User;
import org.example.teleporti.Utils.Router;

import java.util.stream.Collectors;

public class RideComparisonViewController {

    private final TrajetController trajetController = new TrajetController();
    private final UserController userController = new UserController();
    private final AuthController authController = new AuthController();

    @FXML
    private Label welcome;
    @FXML
    private Slider priceFilter;
    @FXML
    private Label priceValueLabel;
    @FXML
    private Slider ratingFilter;
    @FXML
    private Label ratingValueLabel;
    @FXML
    private Slider co2Filter;
    @FXML
    private Label co2ValueLabel;
    @FXML
    private ComboBox<String> vehicleTypeFilter;
    @FXML
    private HBox comparisonBox;
    @FXML
    private TableView<Trajet> costTable;
    @FXML
    private TableColumn<Trajet, String> driverCol;
    @FXML
    private TableColumn<Trajet, String> vehicleCol;
    @FXML
    private TableColumn<Trajet, String> ratingCol;
    @FXML
    private TableColumn<Trajet, String> priceCol;
    @FXML
    private TableColumn<Trajet, String> co2Col;
    @FXML
    private TableColumn<Trajet, Void> actionCol;

    private User currentUser;
    private ObservableList<Trajet> allTrajets;
    private FilteredList<Trajet> filteredTrajets;

    @FXML
    public void initialize() {
        allTrajets = trajetController.getAllTrajets();

        if (allTrajets == null) {
            allTrajets = javafx.collections.FXCollections.observableArrayList();
            System.out.println("WARNING: getAllTrajets() returned null, using empty list");
        }

        filteredTrajets = new FilteredList<>(allTrajets, p -> true);

        setupFilters();
        setupTable();
        updateUI();
    }

    private void setupFilters() {
        priceFilter.valueProperty().addListener((obs, oldVal, newVal) -> {
            priceValueLabel.setText(String.format("%.0f DT", newVal.doubleValue()));
            applyFilters();
        });

        ratingFilter.valueProperty().addListener((obs, oldVal, newVal) -> {
            ratingValueLabel.setText(String.format("%.1f", newVal.doubleValue()));
            applyFilters();
        });

        co2Filter.valueProperty().addListener((obs, oldVal, newVal) -> {
            co2ValueLabel.setText(String.format("%.0f g", newVal.doubleValue()));
            applyFilters();
        });

        ObservableList<String> vehicleTypes = FXCollections.observableArrayList("Tous");
        vehicleTypes.addAll(allTrajets.stream()
                .map(Trajet::getVehicleType)
                .filter(v -> v != null && !v.isBlank())
                .distinct()
                .collect(Collectors.toList()));
        vehicleTypeFilter.setItems(vehicleTypes);
        vehicleTypeFilter.setValue("Tous");
        vehicleTypeFilter.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
    }

    private void applyFilters() {
        filteredTrajets.setPredicate(trajet -> {
            User driver = userController.getUserById(trajet.getConducteurId());
            boolean matchesPrice = trajet.getPrix() <= priceFilter.getValue();
            boolean matchesRating = driver != null && driver.getRating() >= ratingFilter.getValue();
            boolean matchesCO2 = trajet.getCo2Economise() >= co2Filter.getValue();
            boolean matchesVehicle = vehicleTypeFilter.getValue().equals("Tous") || 
                                     trajet.getVehicleType().equals(vehicleTypeFilter.getValue());

            return matchesPrice && matchesRating && matchesCO2 && matchesVehicle;
        });
        updateUI();
    }

    private void setupTable() {
        driverCol.setCellValueFactory(cellData -> {
            User driver = userController.getUserById(cellData.getValue().getConducteurId());
            return new SimpleStringProperty(driver != null ? driver.getPrenom() + " " + driver.getNom() : "Inconnu");
        });
        vehicleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehicleType()));
        ratingCol.setCellValueFactory(cellData -> {
            User driver = userController.getUserById(cellData.getValue().getConducteurId());
            return new SimpleStringProperty(driver != null ? String.valueOf(driver.getRating()) : "N/A");
        });
        priceCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrix())));
        co2Col.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCo2Economise())));

        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Réserver");
            {
                btn.getStyleClass().add("button-primary");
                btn.setOnAction(event -> {
                    Trajet t = getTableView().getItems().get(getIndex());
                    System.out.println("Booking trajet: " + t.getId());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(btn);
            }
        });

        costTable.setItems(filteredTrajets);
    }

    private void updateUI() {
        comparisonBox.getChildren().clear();
        for (Trajet trajet : filteredTrajets) {
            comparisonBox.getChildren().add(createComparisonCard(trajet));
        }
    }

    private VBox createComparisonCard(Trajet trajet) {
        VBox card = new VBox(10);
        card.getStyleClass().add("tp-card");
        card.setMinWidth(250);
        card.setPrefWidth(250);

        User driver = userController.getUserById(trajet.getConducteurId());

        Label title = new Label(trajet.getPointDepart() + " ➔ " + trajet.getDestination());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label driverInfo = new Label("Chauffeur: " + (driver != null ? driver.getPrenom() + " " + driver.getNom() : "Inconnu"));
        Label rating = new Label("Note: ★ " + (driver != null ? driver.getRating() : "N/A"));
        Label vehicle = new Label("Véhicule: " + trajet.getVehicleType());
        Label price = new Label("Prix: " + trajet.getPrix() + " DT");
        price.setStyle("-fx-text-fill: -tp-accent; -fx-font-weight: bold;");
        Label co2 = new Label("CO₂ Économisé: " + trajet.getCo2Economise() + " g");
        co2.getStyleClass().add("badge-eco");

        Label details = new Label("Détails du trajet:\n- Départ: " + trajet.getPointDepart() + "\n- Arrivée: " + trajet.getDestination() + "\n- Date: " + trajet.getDateHeure());
        details.setWrapText(true);
        details.setStyle("-fx-font-size: 11px; -fx-text-fill: -tp-text-secondary;");

        Button bookBtn = new Button("Réserver");
        bookBtn.getStyleClass().add("button-primary");
        bookBtn.setMaxWidth(Double.MAX_VALUE);

        card.getChildren().addAll(title, driverInfo, rating, vehicle, price, co2, new Separator(), details, bookBtn);
        return card;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (welcome != null) {
            welcome.setText("Bienvenue, " + user.getPrenom() + "!");
        }
    }

    @FXML
    private void onGoToHome() {
        Router.goToUser(currentUser, welcome);
    }

    @FXML
    private void onLogout() {
        Router.handleLogout(currentUser, welcome, authController);
    }
}

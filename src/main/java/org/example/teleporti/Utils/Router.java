package org.example.teleporti.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.teleporti.Controllers.AuthController;
import org.example.teleporti.Entities.User;
import org.example.teleporti.SceneControllers.*;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.List;
import java.util.Optional;

public class Router {
    public static final String LOGIN_VIEW = "/org/example/teleporti/Views/login-view.fxml";
    public static final String REGISTER_VIEW = "/org/example/teleporti/Views/register-view.fxml";
    public static final String PROFILE_VIEW = "/org/example/teleporti/Views/profile-view.fxml";
    public static final String DASHBOARD_VIEW = "/org/example/teleporti/Views/dashboard-view.fxml";
    public static final String STATS_VIEW = "/org/example/teleporti/Views/stats-view.fxml";
    public static final String USER_VIEW = "/org/example/teleporti/Views/user-view.fxml";
    public static final String ADD_VIEW = "/org/example/teleporti/Views/add-view.fxml";
    public static final String DRIVERS_VIEW = "/org/example/teleporti/Views/drivers-view.fxml";
    public static final String HISTORY_VIEW = "/org/example/teleporti/Views/history-view.fxml";
    public static final String MAPS_VIEW = "/org/example/teleporti/Views/maps-view.fxml";
    public static final String USERS_VIEW = "/org/example/teleporti/Views/users-view.fxml";
    public static final String EDIT_USER_MODAL_VIEW = "/org/example/teleporti/Views/Modals/edit-user-modal.fxml";
    public static final String EDIT_RESERVATION_MODAL_VIEW = "/org/example/teleporti/Views/Modals/edit-reservation-modal.fxml";
    public static final String EDIT_TRAJET_MODAL_VIEW = "/org/example/teleporti/Views/Modals/edit-trajet-modal.fxml";
    public static final String ADD_MODAL_VIEW = "/org/example/teleporti/Views/Modals/add-modal.fxml";
    public static final String DELETE_MODAL_VIEW = "/org/example/teleporti/Views/Modals/delete-modal.fxml";
    private static final String ERROR_MODAL_VIEW = "/org/example/teleporti/Views/Modals/error-modal.fxml";
    private static final String MESSAGES_VIEW = "/org/example/teleporti/Views/messages-view.fxml";
    private static final String TRAJETS_VIEW = "/org/example/teleporti/Views/trajets-view.fxml";
    private static final String RESERVATIONS_VIEW = "/org/example/teleporti/Views/reservations-view.fxml";

    private Router() {
    }

    public static void goToProfile(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(PROFILE_VIEW));
            Scene scene = new Scene(loader.load());
            ProfileViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setWelcomeMessage(currentUser.getPrenom());
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToDashboard(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(DASHBOARD_VIEW));
            Scene scene = new Scene(loader.load());
            DashboardViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setWelcomeMessage(currentUser.getPrenom());
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToStats(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(STATS_VIEW));
            Scene scene = new Scene(loader.load());
            StatsViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToUser(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(USER_VIEW));
            Scene scene = new Scene(loader.load());
            UserViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToHistory(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(HISTORY_VIEW));
            Scene scene = new Scene(loader.load());
            HistoryViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToMaps(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(MAPS_VIEW));
            Scene scene = new Scene(loader.load());
            MapsViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToUsers(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(USERS_VIEW));
            Scene scene = new Scene(loader.load());
            UsersViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setWelcomeMessage(currentUser.getPrenom());
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToDrivers(User currentUser, Label welcome, List<User> chauffeurs) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(DRIVERS_VIEW));
            Scene scene = new Scene(loader.load());
            DriversViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setChauffeurs(chauffeurs);
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToAdd(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(ADD_VIEW));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToLogin(Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(LOGIN_VIEW));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToRegister(Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(REGISTER_VIEW));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void goToMessages(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(MESSAGES_VIEW));
            Scene scene = new Scene(loader.load());
            MessagesViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openErrorMessageModal(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(ERROR_MODAL_VIEW));
            Scene scene = new Scene(loader.load());
            ErrorModalController controller = loader.getController();
            controller.setMessage(message);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToTrajets(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(TRAJETS_VIEW));
            Scene scene = new Scene(loader.load());
            TrajetsViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToReservations(User currentUser, Label welcome) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(RESERVATIONS_VIEW));
            Scene scene = new Scene(loader.load());
            ReservationsViewController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            Stage stage = (Stage) welcome.getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleLogout(User currentUser, Label nodeForScene, AuthController authController) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de déconnexion");
        alert.setHeaderText("Déconnexion");
        alert.setContentText("Voulez-vous vraiment vous déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (currentUser != null) {
                    authController.logout(currentUser.getId());
                }
                goToLogin(nodeForScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


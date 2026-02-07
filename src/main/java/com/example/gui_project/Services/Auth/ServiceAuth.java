package org.example.teleporti.Services.Auth;

import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.User;

import java.sql.*;
import java.util.UUID;

public class ServiceAuth implements IServiceAuth {

    private final UserController userController = new UserController();
    private Statement ste;
    int size = 0;

    public ServiceAuth(Connection con) {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        String req = "select * from users where email='" + email + "' and password='" + password + "'";
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                return new User(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getString("type"),
                        res.getString("governerat"),
                        res.getString("ville"),
                        res.getString("addresse"),
                        res.getString("telephone"),
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean connection(String email, String password) {
        User user = getUserByEmailAndPassword(email, password);
        return user != null;
    }

    public void saveSessionToken(int userId) {
        String sessionToken = UUID.randomUUID().toString();
        String query = "UPDATE users SET token = ? WHERE id = ?";
        try (PreparedStatement statement = ste.getConnection().prepareStatement(query)) {
            statement.setString(1, sessionToken);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearSessionToken(int userId) {
        String query = "UPDATE users SET token = NULL WHERE id = ?";
        try (PreparedStatement statement = ste.getConnection().prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateSession(String sessionToken) {
        String query = "SELECT id FROM users WHERE token = ?";
        try (PreparedStatement statement = ste.getConnection().prepareStatement(query)) {
            statement.setString(1, sessionToken);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean inscription(User user) {
        return userController.ajout(user);
    }

    @Override
    public void logout(int userId) {
        clearSessionToken(userId);
    }

    @Override
    public String getSessionToken(int userId) {
        String query = "SELECT token FROM users WHERE id = ?";
        try (PreparedStatement statement = ste.getConnection().prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("token");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getUserIdBySessionToken(String sessionToken) {
        String query = "SELECT id FROM users WHERE token = ?";
        try (PreparedStatement statement = ste.getConnection().prepareStatement(query)) {
            statement.setString(1, sessionToken);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
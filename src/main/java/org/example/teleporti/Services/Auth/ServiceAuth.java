package org.example.teleporti.Services.Auth;

import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.User;
import java.util.prefs.Preferences;

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
        String req = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement ps = ste.getConnection().prepareStatement(req)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
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
                        0.0f,
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User getUserById(int id) {
        String req = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = ste.getConnection().prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
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
                        0.0f,
                        res.getDate("creation_date"),
                        res.getDate("update_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean connection(String email, String password) {
        User user = getUserByEmailAndPassword(email, password);
        return user != null;
    }

    public String saveSessionToken(int userId) {
        String sessionToken = UUID.randomUUID().toString();
        String query = "UPDATE users SET token = ? WHERE id = ?";
        try (PreparedStatement statement = ste.getConnection().prepareStatement(query)) {
            statement.setString(1, sessionToken);
            statement.setInt(2, userId);
            statement.executeUpdate();
            return sessionToken; // ✅ return token so we can store it locally
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        clearLocalToken();
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
    private static final String PREF_TOKEN_KEY = "sessionToken";
    private final Preferences prefs = Preferences.userNodeForPackage(ServiceAuth.class);

    public void saveTokenLocally(String token) {
        if (token != null && !token.isBlank()) {
            prefs.put(PREF_TOKEN_KEY, token);
        }
    }
    public User tryAutoLogin() {
        String token = readLocalToken();
        if (token == null) return null;

        if (validateSession(token)) {
            int userId = getUserIdBySessionToken(token);
            // If you have a getUserById, use it. If not, create one.
            return getUserById(userId);
        } else {
            clearLocalToken();
            return null;
        }
    }
    public User login(String email, String password, boolean stayConnected) {
        User user = getUserByEmailAndPassword(email, password);
        if (user == null) return null;

        if (stayConnected) {
            String token = saveSessionToken(user.getId());
            saveTokenLocally(token);
        } else {
            clearSessionToken(user.getId());
            clearLocalToken();
        }
        return user;
    }
    public String readLocalToken() {
        return prefs.get(PREF_TOKEN_KEY, null);
    }

    public void clearLocalToken() {
        prefs.remove(PREF_TOKEN_KEY);
    }
}

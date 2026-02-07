package org.example.teleporti.Controllers;

import org.example.teleporti.Entities.User;
import org.example.teleporti.SceneControllers.LoginViewController;
import org.example.teleporti.Services.Auth.ServiceAuth;
import org.example.teleporti.Utils.DatabaseConnection;

import java.sql.Connection;
import java.util.prefs.Preferences;

public class AuthController {
    private static final Connection con = new DatabaseConnection().getConnection();
    private static final ServiceAuth _serviceAuth = new ServiceAuth(con);

    public boolean connection(String email, String password, boolean staySignedIn) {
        User user = _serviceAuth.getUserByEmailAndPassword(email, password);
        if (user != null) {
            if (staySignedIn) {
                saveSessionToken(user.getId());
                Preferences prefs = Preferences.userNodeForPackage(LoginViewController.class);
                prefs.put("sessionToken", getSessionToken(user.getId()));
            }
            return true;
        }
        return false;
    }

    public void saveSessionToken(int userId) {
        _serviceAuth.saveSessionToken(userId);
    }

    public boolean validateSession(String sessionToken) {
        return _serviceAuth.validateSession(sessionToken);
    }

    public boolean inscription(User newUser) {
        return _serviceAuth.inscription(newUser);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return _serviceAuth.getUserByEmailAndPassword(email, password);
    }

    public void logout(int userId) {
        _serviceAuth.logout(userId);
    }

    public String getSessionToken(int userId) {
        return _serviceAuth.getSessionToken(userId);
    }
}
package org.example.teleporti.Controllers;

import org.example.teleporti.MainApplication;
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
        if (user == null) return false;

        // ✅ Use the SAME node everywhere (match ServiceAuth)
        Preferences prefs = Preferences.userNodeForPackage(ServiceAuth.class);

        if (staySignedIn) {
            String token = _serviceAuth.saveSessionToken(user.getId()); // ✅ use returned token
            if (token != null) {
                prefs.put("sessionToken", token);
            }
        } else {
            prefs.remove("sessionToken");
            // Optional: clear token in DB too (your choice)
            _serviceAuth.clearSessionToken(user.getId());
        }
        return true;
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

    public User getUserByToken(String token) {
        int userId = _serviceAuth.getUserIdBySessionToken(token);
        if (userId != -1) {
            return _serviceAuth.getUserById(userId);        }
        return null;
    }
    
    public void logout(int userId) {
        _serviceAuth.logout(userId);
        Preferences prefs = Preferences.userNodeForPackage(ServiceAuth.class);
        prefs.remove("sessionToken");
    }

    public String getSessionToken(int userId) {
        return _serviceAuth.getSessionToken(userId);
    }
}

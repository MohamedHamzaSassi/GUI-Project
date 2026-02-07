package org.example.teleporti.Services.Auth;

import org.example.teleporti.Entities.User;

public interface IServiceAuth {

    User getUserByEmailAndPassword(String email, String password);

    boolean connection(String email, String password);

    boolean inscription(User user);

    void logout(int userId);

    String getSessionToken(int userId);
}

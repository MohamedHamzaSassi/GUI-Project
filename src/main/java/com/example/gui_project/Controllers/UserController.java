package org.example.teleporti.Controllers;

import org.example.teleporti.Entities.User;
import org.example.teleporti.Services.User.ServiceUser;
import org.example.teleporti.Utils.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

public class UserController {
    private static Connection con = new DatabaseConnection().getConnection();
    private static ServiceUser _serviceUser = new ServiceUser(con);

    public boolean ajout(User user) {
        return _serviceUser.ajout(user);
    }

    public void afficher() {
        _serviceUser.afficher();
    }

    public List<User> afficherList() {
        return _serviceUser.afficherList();
    }

    public void modifier(User user) {
        _serviceUser.modifier(user);
    }

    public void supprimer(User user) {
        _serviceUser.supprimer(user);
    }

    public int getSize() {
        return _serviceUser.getSize();
    }

    public void truncate() {
        _serviceUser.truncate();
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return _serviceUser.getUserByEmailAndPassword(email, password);
    }

    public List<User> rechercher(String search) {
        return _serviceUser.rechercher(search);
    }

    public int countByType(String type) {
        return _serviceUser.countByType(type);
    }

    public int countByGovernerat(String governerat) {
        return _serviceUser.countByGovernerat(governerat);
    }

    public List<User> getAllChauffeurs() {
        return _serviceUser.getAllChauffeurs();
    }

    public List<User> getAllClients() {
        return _serviceUser.getAllClients();
    }

    public List<String> getAllGovernerats() {
        return _serviceUser.getAllGovernerats();
    }

    public void createUserTableInDatabase() {
        _serviceUser.createUsersTableInDatabase();
    }

    public User getUserById(int id) {
        return _serviceUser.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return _serviceUser.getUserByEmail(email);
    }

    public User getUserByPrenomAndNom(String prenom, String nom) {
        return _serviceUser.getUserByPrenomAndNom(prenom, nom);
    }
}

package org.example.teleporti.Controllers;

import javafx.collections.ObservableList;
import org.example.teleporti.Entities.Trajet;
import org.example.teleporti.Services.Trajet.ServiceTrajet;
import org.example.teleporti.Utils.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

public class TrajetController {
    private static Connection con = new DatabaseConnection().getConnection();
    private static ServiceTrajet _serviceTrajet = new ServiceTrajet(con);

    public void ajout(Trajet trajet) {
        _serviceTrajet.ajout(trajet);
    }

    public void afficher() {
        _serviceTrajet.afficher();
    }

    public void modifier(Trajet trajet) {
        _serviceTrajet.modifier(trajet);
    }

    public void supprimer(Trajet trajet) {
        _serviceTrajet.supprimer(trajet);
    }

    public int getSize() {
        return _serviceTrajet.getSize();
    }

    public void truncate() {
        _serviceTrajet.truncate();
    }

    public List<Trajet> getTrajetsByUserId(int id) {
        return _serviceTrajet.getTrajetsByUserId(id);
    }

    public Double countAvgCO2EmissionByGovernerat(String governerat) {
        return _serviceTrajet.countAvgCO2EmissionByGovernerat(governerat);
    }

    public Double countAvgRideCostByGovernerat(String governerat) {
        return _serviceTrajet.countAvgRideCostByGovernerat(governerat);
    }

    public ObservableList<Trajet> getAllTrajets() {
        return _serviceTrajet.getAllTrajets();
    }

    public Trajet rechercher(String newValue) {
        return _serviceTrajet.rechercher(newValue);
    }

    public void createTrajetTable() {
        _serviceTrajet.createTrajetTable();
    }

    public Trajet getTrajetById(int id) {
        return _serviceTrajet.getTrajetById(id);
    }
}

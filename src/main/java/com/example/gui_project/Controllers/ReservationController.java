package org.example.teleporti.Controllers;

import javafx.collections.ObservableList;
import org.example.teleporti.Entities.Reservation;
import org.example.teleporti.Services.Reservation.ServiceReservation;
import org.example.teleporti.Utils.DatabaseConnection;

import java.sql.Connection;

public class ReservationController {
    private static Connection con = new DatabaseConnection().getConnection();
    private static ServiceReservation _serviceReservation = new ServiceReservation(con);

    public void ajout(Reservation reservation) {
        _serviceReservation.ajout(reservation);
    }

    public void afficher() {
        _serviceReservation.afficher();
    }

    public void modifier(Reservation reservation) {
        _serviceReservation.modifier(reservation);
    }

    public void supprimer(Reservation reservation) {
        _serviceReservation.supprimer(reservation);
    }

    public int getSize() {
        return _serviceReservation.getSize();
    }

    public void truncate() {
        _serviceReservation.truncate();
    }

    public ObservableList<Reservation> getAllReservations() {
        return _serviceReservation.getAllReservations();
    }

    public void createReservationTable() {
        _serviceReservation.createReservationTable();
    }
}

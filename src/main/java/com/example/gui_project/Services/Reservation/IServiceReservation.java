package org.example.teleporti.Services.Reservation;

import org.example.teleporti.Entities.Reservation;

import java.util.List;

public interface IServiceReservation {
    void ajout(Reservation newReservation);

    List<Reservation> afficher();

    void modifier(Reservation reservation);

    void supprimer(Reservation reservation);
}

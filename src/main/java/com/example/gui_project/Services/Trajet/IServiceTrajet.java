package org.example.teleporti.Services.Trajet;


import org.example.teleporti.Entities.Trajet;

import java.util.List;

public interface IServiceTrajet {
    void ajout(Trajet newTrajet);

    Object afficher();

    void modifier(Trajet trajet);

    void supprimer(Trajet trajet);

    List<Trajet> getTrajetsByUserId(int id);

    Double countAvgCO2EmissionByGovernerat(String governerat);

    Double countAvgRideCostByGovernerat(String governerat);

    List<Trajet> getAllTrajets();

    Trajet getTrajetById(int id);
}

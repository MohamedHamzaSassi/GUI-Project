package org.example.teleporti.Utils;

import org.controlsfx.control.WorldMapView.Location;

import java.util.List;

public class Constants {

    public static final List<Location> locations = List.of(
            new Location("Tunis", 36.8065, 10.1815),
            new Location("Sousse", 35.8252, 10.6367),
            new Location("Sfax", 34.7487, 10.6500),
            new Location("Gabes", 33.8814, 10.0982),
            new Location("Bizerte", 37.2744, 9.8733),
            new Location("Kairouan", 35.6784, 10.0967),
            new Location("Gafsa", 34.425, 8.7843),
            new Location("Monastir", 35.7833, 10.8333),
            new Location("Jendouba", 36.5, 8.7833),
            new Location("Kebili", 33.7, 8.9667),
            new Location("Mahdia", 35.5167, 11.0833),
            new Location("Manouba", 36.8, 10.1),
            new Location("Medenine", 33.4, 10.5),
            new Location("Nabeul", 36.45, 10.7333),
            new Location("Sidi Bouzid", 35.0333, 9.4833),
            new Location("Siliana", 36.0833, 9.3667),
            new Location("Tataouine", 32.9333, 10.45),
            new Location("Tozeur", 33.9167, 8.1333),
            new Location("Zaghouan", 36.4, 10.15),
            new Location("Ariana", 36.8667, 10.2),
            new Location("Ben Arous", 36.75, 10.2167),
            new Location("Kasserine", 35.1667, 8.8333),
            new Location("Kef", 36.1667, 8.7167)
    );

    public static final List<String> roles = List.of(
            "Admin",
            "Client",
            "Chauffeur",
            "Manager"
    );

    public static final List<String> statuses = List.of(
            "En attente",
            "Acceptée",
            "Refusée",
            "Annulée",
            "Terminée",
            "En cours"
    );
}

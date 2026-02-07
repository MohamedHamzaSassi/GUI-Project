package org.example.teleporti.Services.Trajet;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.teleporti.Entities.Trajet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceTrajet implements IServiceTrajet {

    private Statement ste;
    int size = 0;

    public ServiceTrajet(Connection con) {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void truncate() {
        String req = "truncate table trajets";
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajout(Trajet newTrajet) {
        String req =
                "insert into trajets (conducteurId, pointDepart, destination, dateHeure, placesDisponibles ,co2Economise, prix) values ('" +
                        newTrajet.getConducteurId() + "', '" +
                        newTrajet.getPointDepart() + "', '" +
                        newTrajet.getDestination() + "', '" +
                        newTrajet.getDateHeure() + "', '" +
                        newTrajet.getPlacesDisponibles() + "', '" +
                        newTrajet.getCo2Economise() + "', '" +
                        newTrajet.getPrix()
                        + "')";
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Trajet> afficher() {
        String req = "select id, conducteurId, pointDepart, destination, dateHeure, placesDisponibles, co2Economise from trajets";
        ArrayList<Trajet> trajets = new ArrayList<>();
        try {
            if (getSize() == 0) {
                System.out.println("Aucun trajet trouvé");
            } else {
                ResultSet res = ste.executeQuery(req);
                while (res.next()) {
                    System.out.println("ID: " + res.getInt("id") +
                            "\nConducteur ID: " + res.getInt("conducteurId") +
                            "\nPoint de départ: " + res.getString("pointDepart") +
                            "\nDestination: " + res.getString("destination") +
                            "\nDate et heure: " + res.getDate("dateHeure") +
                            "\nPlaces disponibles: " + res.getInt("placesDisponibles") +
                            "\nCO2 économisé: " + res.getFloat("co2Economise") +
                            "\nPrix: " + res.getFloat("prix")
                    );
                    trajets.add(new Trajet(
                            res.getInt("id"),
                            res.getInt("conducteurId"),
                            res.getString("pointDepart"),
                            res.getString("destination"),
                            res.getDate("dateHeure"),
                            res.getInt("placesDisponibles"),
                            res.getFloat("co2Economise"),
                            res.getFloat("prix")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return trajets;
    }

    @Override
    public void modifier(Trajet trajet) {
        String req = "update trajets set conducteurId = '" + trajet.getConducteurId() +
                "', pointDepart = '" + trajet.getPointDepart() +
                "', destination = '" + trajet.getDestination() +
                "', dateHeure = '" + trajet.getDateHeure() +
                "', placesDisponibles = '" + trajet.getPlacesDisponibles() +
                "', co2Economise = '" + trajet.getCo2Economise() +
                "' where id = " + trajet.getId();
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Trajet trajet) {
        String req = "delete from trajets where id = " + trajet.getId();
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Get all trajets by user ID
     *
     * @param id
     * @return
     */
    @Override
    public List<Trajet> getTrajetsByUserId(int id) {
        String req = "select * from trajets where conducteurId = " + id;
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                System.out.println("ID: " + res.getInt("id") +
                        "\nConducteur ID: " + res.getInt("conducteurId") +
                        "\nPoint de départ: " + res.getString("pointDepart") +
                        "\nDestination: " + res.getString("destination") +
                        "\nDate et heure: " + res.getDate("dateHeure") +
                        "\nPlaces disponibles: " + res.getInt("placesDisponibles") +
                        "\nCO2 économisé: " + res.getFloat("co2Economise")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getSize() {
        String req = "select count(*) from trajets";
        try {
            ResultSet result = ste.executeQuery(req);
            while (result.next()) {
                size = result.getInt(1);
            }
            return size;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public Double countAvgCO2EmissionByGovernerat(String governerat) {
        String req = "select avg(co2Economise) from trajets where pointDepart = '" + governerat + "'";
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                return res.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0.0;
    }

    /**
     * @param governerat
     * @return
     */
    @Override
    public Double countAvgRideCostByGovernerat(String governerat) {
        String req = "select avg(prix) from trajets where pointDepart = '" + governerat + "'";
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                return res.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0.0;
    }

    /**
     * @return
     */
    @Override
    public ObservableList<Trajet> getAllTrajets() {
        String req = "select * from trajets";
        ObservableList<Trajet> trajets = FXCollections.observableArrayList();
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                trajets.add(new Trajet(
                        res.getInt("id"),
                        res.getInt("conducteurId"),
                        res.getString("pointDepart"),
                        res.getString("destination"),
                        res.getDate("dateHeure"),
                        res.getInt("placesDisponibles"),
                        res.getFloat("co2Economise"),
                        res.getFloat("prix")
                ));
            }
            return trajets;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Trajet getTrajetById(int id) {
        String req = "select * from trajets where id = " + id;
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                return new Trajet(
                        res.getInt("id"),
                        res.getInt("conducteurId"),
                        res.getString("pointDepart"),
                        res.getString("destination"),
                        res.getDate("dateHeure"),
                        res.getInt("placesDisponibles"),
                        res.getFloat("co2Economise"),
                        res.getFloat("prix")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Trajet rechercher(String newValue) {
        String req = "select * from trajets where pointDepart = '" + newValue + "'";
        try {
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                return new Trajet(
                        res.getInt("id"),
                        res.getInt("conducteurId"),
                        res.getString("pointDepart"),
                        res.getString("destination"),
                        res.getDate("dateHeure"),
                        res.getInt("placesDisponibles"),
                        res.getFloat("co2Economise"),
                        res.getFloat("prix")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void createTrajetTable() {
        String req = "CREATE TABLE IF NOT EXISTS trajets (" +
                "id int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "conducteurId int NOT NULL," +
                "pointDepart varchar(255) NOT NULL," +
                "destination varchar(255) NOT NULL," +
                "dateHeure date NOT NULL," +
                "placesDisponibles int NOT NULL," +
                "co2Economise float NOT NULL," +
                "prix float NOT NULL" +
                ")";
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

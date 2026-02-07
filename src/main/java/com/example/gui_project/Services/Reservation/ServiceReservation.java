package org.example.teleporti.Services.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.teleporti.Entities.Reservation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation implements IServiceReservation {

    private Statement ste;
    int size = 0;

    public ServiceReservation(Connection con) {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void truncate() {
        String req = "truncate table reservations";
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajout(Reservation newReservation) {
        String req = "insert into reservations (trajetId, passagerId, status) values ('" + newReservation.getTrajetId() + "', '" + newReservation.getPassagerId() + "', '" + newReservation.getStatus() + "')";
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reservation> afficher() {
        String req = "select id, trajetId, passagerId, status from reservations";
        ArrayList<Reservation> reservations = new ArrayList<>();
        try {
            if (getSize() == 0) {
                System.out.println("Aucun reservation trouvée");
            } else {
                ResultSet res = ste.executeQuery(req);
                while (res.next()) {
                    reservations.add(new Reservation(res.getInt("id"), res.getInt("trajetId"), res.getInt("passagerId"), res.getString("status")));
                    System.out.println("ID: " + res.getInt("id") + "\nTrajet ID: " + res.getInt("trajetId") + "\nPassager ID: " + res.getInt("passagerId") + "\nStatus: " + res.getString("status"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservations;
    }

    @Override
    public void modifier(Reservation reservation) {
        String req = "update reservations set trajetId = '" + reservation.getTrajetId() + "', passagerId = '" + reservation.getPassagerId() + "', status = '" + reservation.getStatus() + "' where id = " + reservation.getId();
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Reservation reservation) {
        String req = "delete from reservations where id = " + reservation.getId();
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public int getSize() {
        String req = "select count(*) from reservations";
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

    public ObservableList<Reservation> getAllReservations() {
        String req = "select id, trajetId, passagerId, status from reservations";
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        try {
            if (getSize() == 0) {
                System.out.println("Aucun reservation trouvée");
            } else {
                ResultSet res = ste.executeQuery(req);
                while (res.next()) {
                    reservations.add(new Reservation(res.getInt("id"), res.getInt("trajetId"), res.getInt("passagerId"), res.getString("status")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservations;
    }

    public void createReservationTable() {
        String req = "create table if not exists reservations (id int primary key auto_increment, trajetId int, passagerId int, status varchar(255))";
        try {
            ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

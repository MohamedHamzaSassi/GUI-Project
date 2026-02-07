package org.example.teleporti;

import org.example.teleporti.Controllers.MessageController;
import org.example.teleporti.Controllers.ReservationController;
import org.example.teleporti.Controllers.TrajetController;
import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.Message;
import org.example.teleporti.Entities.Reservation;
import org.example.teleporti.Entities.Trajet;
import org.example.teleporti.Entities.User;

public class Main {

    private static final UserController _userController = new UserController();
    private static final TrajetController _trajetController = new TrajetController();
    private static final ReservationController _reservationController = new ReservationController();
    private static final MessageController _messageController = new MessageController();

    public static void main(String[] args) {

        _userController.createUserTableInDatabase();
        _messageController.createMessagesTable();
        _trajetController.createTrajetTable();
        _reservationController.createReservationTable();


        User user1 = new User(_userController.getSize() + 1, "Sassi", "Mohamed Hamza", 26, "mohamedhamzasassi@gmail.com", "hamza123", "Admin", "Tunis", "Tunis", "Tunis", "12345678");
        _userController.ajout(user1);

        User user2 = new User(_userController.getSize() + 1, "HAMDI", "Mohamed Amine", 25, "mohamedAmineHamdi@gmail.com", "amine123", "Chauffeur", "Tunis", "Tunis", "Jaafer", "12345678");
        _userController.ajout(user2);
        

        Trajet trajet1 = new Trajet(_trajetController.getSize() + 1, user1.getId(), "Tunis", "Sousse", 3, 20, 50.0f);
        _trajetController.ajout(trajet1);

        Trajet trajet2 = new Trajet(_trajetController.getSize() + 1, user1.getId(), "Tunis", "Sfax", 4, 30, 70.0f);
        _trajetController.ajout(trajet2);

        Trajet trajet3 = new Trajet(_trajetController.getSize() + 1, user1.getId(), "Tunis", "Bizerte", 1, 10, 30.0f);
        _trajetController.ajout(trajet3);

        Reservation reservation1 = new Reservation(_reservationController.getSize() + 1, user1.getId(), trajet1.getId(), "En cours");
        _reservationController.ajout(reservation1);

        Reservation reservation2 = new Reservation(_reservationController.getSize() + 1, user2.getId(), trajet1.getId(), "En cours");
        _reservationController.ajout(reservation2);

        Reservation reservation3 = new Reservation(_reservationController.getSize() + 1, user1.getId(), trajet2.getId(), "En cours");
        _reservationController.ajout(reservation3);

        Message message1 = new Message(
                _messageController.getSize() + 1,
                "Hello World",
                2,
                4
        );
        _messageController.ajout(message1);

        // _userController.truncate();
        _userController.afficher();
        // _trajetController.truncate();
        _trajetController.afficher();
        // _reservationController.truncate();
        _reservationController.afficher();

    }

}
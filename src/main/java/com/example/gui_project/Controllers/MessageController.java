package org.example.teleporti.Controllers;

import org.example.teleporti.Entities.Message;
import org.example.teleporti.Services.Message.ServiceMessage;
import org.example.teleporti.Utils.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

public class MessageController {
    private static Connection con = new DatabaseConnection().getConnection();
    private static ServiceMessage _serviceMessage = new ServiceMessage(con);

    public boolean ajout(Message message) {
        return _serviceMessage.ajout(message);
    }

    public void afficher() {
        _serviceMessage.afficher();
    }

    public List<Message> afficherList() {
        return _serviceMessage.afficher();
    }

    public void modifier(Message message) {
        _serviceMessage.modifier(message);
    }

    public void supprimer(Message message) {
        _serviceMessage.supprimer(message);
    }

    public int getSize() {
        return _serviceMessage.getSize();
    }

    public void truncate() {
        _serviceMessage.truncate();
    }

    public List<Message> rechercher(String search) {
        return _serviceMessage.rechercher(search);
    }

    public List<Message> getAllCurrentUserConversations(int currentUserId) {
        return _serviceMessage.getAllCurrentUserConversations(currentUserId);
    }

    public List<Message> getConversationBetweenTwoUsers(int senderId, int recieverId) {
        return _serviceMessage.getConversationBetweenTwoUsers(senderId, recieverId);
    }

    public void createMessagesTable() {
        _serviceMessage.createMessagesTable();
    }
}

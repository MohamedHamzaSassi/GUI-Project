package org.example.teleporti.Services.Message;

import org.example.teleporti.Controllers.UserController;
import org.example.teleporti.Entities.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMessage implements IServiceMessage {

    private final UserController userController = new UserController();
    private Statement ste;
    int size = 0;

    public ServiceMessage(Connection con) {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Cette fonction permet d'ajouter une Message a la base de données
     *
     * @param newMessage la nouvelle message a ajoutée
     */
    @Override
    public boolean ajout(Message newMessage) {
        String req = "INSERT INTO messages (messageContent, senderId, recieverId, sentAt, updatedAt) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = ste.getConnection().prepareStatement(req)) {
            pstmt.setString(1, newMessage.getMessageContent());
            pstmt.setInt(2, newMessage.getSenderId());
            pstmt.setInt(3, newMessage.getRecieverId());
            pstmt.setTimestamp(4, new Timestamp(newMessage.getSentAt().getTime()));
            pstmt.setTimestamp(5, new Timestamp(newMessage.getUpdatedAt().getTime()));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cette fonction permet d'afficher tous les messages de la base de données.
     *
     * @return une liste de tous les messages
     */
    @Override
    public List<Message> afficher() {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages";
        try (ResultSet rs = ste.executeQuery(query)) {
            while (rs.next()) {
                messages.add(new Message(rs.getInt("id"), rs.getString("messageContent"), rs.getInt("senderId"), rs.getInt("recieverId"), rs.getDate("sentAt"), rs.getDate("updatedAt")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    /**
     * Cette fonction permet de modifier une message dans la base de données.
     *
     * @param message la message modifiée
     * @return true si la message a été modifiée, false sinon
     */
    @Override
    public boolean modifier(Message message) {
        String req = "UPDATE messages SET messageContent = ?, senderId = ?, recieverId = ?, sentAt = ?, updatedAt = ? WHERE id = ?";
        try (PreparedStatement pstmt = ste.getConnection().prepareStatement(req)) {
            pstmt.setString(1, message.getMessageContent());
            pstmt.setInt(2, message.getSenderId());
            pstmt.setInt(3, message.getRecieverId());
            pstmt.setTimestamp(4, new Timestamp(message.getSentAt().getTime()));
            pstmt.setTimestamp(5, new Timestamp(message.getUpdatedAt().getTime()));
            pstmt.setInt(6, message.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cette fonction permet de supprimer un message de la base de données.
     *
     * @param message le message à supprimer
     * @return true si le message a été supprimé avec succès, false sinon
     */
    @Override
    public boolean supprimer(Message message) {
        String req = "DELETE FROM messages WHERE id = ?";
        try (PreparedStatement pstmt = ste.getConnection().prepareStatement(req)) {
            pstmt.setInt(1, message.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cette fonction permet de récupérer un message par l'identifiant de l'expéditeur et du destinataire.
     *
     * @param senderId   l'identifiant de l'expéditeur
     * @param recieverId l'identifiant du destinataire
     * @return le message correspondant ou null si aucun message n'est trouvé
     */
    @Override
    public Message getMessageBySenderIdAndRecieverId(int senderId, int recieverId) {
        String query = "SELECT * FROM messages WHERE senderId = ? AND recieverId = ? ORDER BY sentAt DESC";
        try (PreparedStatement pstmt = ste.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, recieverId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Message(rs.getInt("id"), rs.getString("messageContent"), rs.getInt("senderId"), rs.getInt("recieverId"), rs.getDate("sentAt"), rs.getDate("updatedAt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param currentUserId
     * @return
     */
    @Override
    public List<Message> getAllCurrentUserConversations(int currentUserId) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE senderId = ? OR recieverId = ? ORDER BY sentAt DESC";
        try (PreparedStatement pstmt = ste.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, currentUserId);
            pstmt.setInt(2, currentUserId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getInt("id"), rs.getString("messageContent"), rs.getInt("senderId"), rs.getInt("recieverId"), rs.getDate("sentAt"), rs.getDate("updatedAt")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    /**
     * Retrieves all messages exchanged between two users
     *
     * @param senderId   ID of the first user
     * @param recieverId ID of the second user
     * @return List of messages between the two users ordered by sent time
     * @throws SQLException if database error occurs
     */
    @Override
    public List<Message> getConversationBetweenTwoUsers(int senderId, int recieverId) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE (senderId = ? AND recieverId = ?) OR (senderId = ? AND recieverId = ?) " +
                "ORDER BY sentAt ASC";

        try (PreparedStatement pstmt = ste.getConnection().prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY)) {

            pstmt.setInt(1, senderId);
            pstmt.setInt(2, recieverId);
            pstmt.setInt(3, recieverId);
            pstmt.setInt(4, senderId);
            pstmt.setFetchSize(1000); // Optimize for large result sets

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    messages.add(new Message(
                            rs.getInt("id"),
                            rs.getString("messageContent"),
                            rs.getInt("senderId"),
                            rs.getInt("recieverId"),
                            rs.getDate("sentAt"), // Use Timestamp instead of Date
                            rs.getDate("updatedAt")
                    ));
                }
            }
        } catch (SQLException e) {
            // Log error instead of just printing stack trace
            e.printStackTrace();
            throw new RuntimeException("Error retrieving conversation messages", e);
        }
        return messages;
    }

    public void createMessagesTable() {
        String req = "create table messages(" + "id int(11) primary key auto_increment," + "messageContent text not null," + "senderId int(11) not null," + "recieverId int(11) not null," + "sentAt datetime," + "updatedAt datetime" + ")";
        try {
            ste.execute(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        String req = "select count(*) from messages";
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

    public void truncate() {
    }

    public List<Message> rechercher(String search) {
        List<Message> messageList = new ArrayList<Message>();
        return messageList;
    }
}
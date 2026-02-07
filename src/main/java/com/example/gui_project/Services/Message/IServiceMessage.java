package org.example.teleporti.Services.Message;

import org.example.teleporti.Entities.Message;

import java.util.List;

public interface IServiceMessage {

    boolean ajout(Message newMessage);

    List<Message> afficher();

    boolean modifier(Message message);

    boolean supprimer(Message message);

    Message getMessageBySenderIdAndRecieverId(int senderId, int recieverId);

    List<Message> getAllCurrentUserConversations(int currentUserId);

    List<Message> getConversationBetweenTwoUsers(int senderId, int recieverId);
}

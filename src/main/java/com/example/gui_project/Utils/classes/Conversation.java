package org.example.teleporti.Utils.classes;

import org.example.teleporti.Entities.Message;

import java.util.List;

public class Conversation {
    private String talkingTo;
    private List<Message> messages;

    public Conversation(String talkingTo, List<Message> messages) {
        this.talkingTo = talkingTo;
        this.messages = messages;
    }

    public Conversation() {
    }

    public String getTalkingTo() {
        return talkingTo;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setTalkingTo(String talkingTo) {
        this.talkingTo = talkingTo;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

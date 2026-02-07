package org.example.teleporti.Entities;

import java.sql.Date;
import java.util.Objects;

public class Message {

    protected int id;
    protected String messageContent;
    protected int senderId;
    protected int recieverId;
    protected Date sentAt = new Date(new java.util.Date().getTime());
    protected Date updatedAt = new Date(new java.util.Date().getTime());

    public Message() {
    }

    public Message(int id, String messageContent, int senderId, int recieverId, Date sentAt, Date updatedAt) {
        this.id = id;
        this.messageContent = messageContent;
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.sentAt = sentAt;
        this.updatedAt = updatedAt;
    }

    public Message(int id, String messageContent, int senderId, int recieverId) {
        this.id = id;
        this.messageContent = messageContent;
        this.senderId = senderId;
        this.recieverId = recieverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(int recieverId) {
        this.recieverId = recieverId;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message message)) return false;
        return getId() == message.getId() && getSenderId() == message.getSenderId() && getRecieverId() == message.getRecieverId() && Objects.equals(getMessageContent(), message.getMessageContent()) && Objects.equals(getSentAt(), message.getSentAt()) && Objects.equals(getUpdatedAt(), message.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMessageContent(), getSenderId(), getRecieverId(), getSentAt(), getUpdatedAt());
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageContent='" + messageContent + '\'' +
                ", senderId=" + senderId +
                ", recieverId=" + recieverId +
                ", sentAt=" + sentAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

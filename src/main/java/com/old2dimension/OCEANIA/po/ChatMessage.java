package com.old2dimension.OCEANIA.po;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "sender_id")
    private int senderId;
    @Column(name = "recipient_id")
    private int recipientId;
    @Column(name = "send_date")
    private Date sendDate;
    @Column(name = "has_read")
    private int has_read;

    public ChatMessage(){}

    public ChatMessage(int senderId, int recipientId, Date sendDate, int has_read) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.sendDate = sendDate;
        this.has_read = has_read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public int getHas_read() {
        return has_read;
    }

    public void setHas_read(int has_read) {
        this.has_read = has_read;
    }

}
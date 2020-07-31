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
    private int hasRead;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "content")
    private String content;

    public ChatMessage(){}

    public ChatMessage(int senderId, int recipientId, Date sendDate, int hasRead) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.sendDate = sendDate;
        this.hasRead = hasRead;
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

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

}
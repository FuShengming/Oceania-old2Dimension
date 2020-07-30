package com.old2dimension.OCEANIA.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat_workspace")
public class ChatWorkSpace {
    public ChatWorkSpace() {
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "chatting_list")
    private String chattingList;
    @Column(name = "date")
    private Date date;

    public ChatWorkSpace(int userId, String chattingList, Date date) {
        this.userId = userId;
        this.chattingList = chattingList;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getChattingList() {
        return chattingList;
    }

    public void setChattingList(String chattingList) {
        this.chattingList = chattingList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

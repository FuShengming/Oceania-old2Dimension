package com.old2dimension.OCEANIA.po;

import javax.persistence.*;

@Entity
@Table(name = "team_announcement_read")
public class AnnouncementRead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "announcement_id")
    private int announcementId;
    @Column(name = "has_read")
    private int hasRead;

    public AnnouncementRead(){}

    public AnnouncementRead(int userId, int announcementId, int hasRead) {
        this.userId = userId;
        this.announcementId = announcementId;
        this.hasRead = hasRead;
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

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }
}

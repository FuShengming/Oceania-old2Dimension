package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Announcement;

public class AnnouncementAndUserReadForm {
    public AnnouncementAndUserReadForm(Announcement announcement, int userId, int hasRead) {
        this.announcement = announcement;
        this.userId = userId;
        this.hasRead = hasRead;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    private Announcement announcement;
    private int userId;
    private int hasRead;


}

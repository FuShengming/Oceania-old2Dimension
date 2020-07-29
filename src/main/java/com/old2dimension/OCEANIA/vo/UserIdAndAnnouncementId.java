package com.old2dimension.OCEANIA.vo;

public class UserIdAndAnnouncementId {
    public UserIdAndAnnouncementId(int userId, int announcementId) {
        this.userId = userId;
        this.announcementId = announcementId;
    }

    private int userId;

    private int announcementId;

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

}

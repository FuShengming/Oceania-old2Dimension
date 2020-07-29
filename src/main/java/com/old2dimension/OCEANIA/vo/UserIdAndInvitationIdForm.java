package com.old2dimension.OCEANIA.vo;

public class UserIdAndInvitationIdForm {
    private int userId;

    private int invitationId;

    public UserIdAndInvitationIdForm(int userId, int invitationId) {
        this.userId = userId;
        this.invitationId = invitationId;
    }

    public UserIdAndInvitationIdForm() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }

}

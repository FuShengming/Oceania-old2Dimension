package com.old2dimension.OCEANIA.po;

import javax.persistence.*;

@Entity
@Table(name = "invitation_message")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "inviter_id")
    private int inviterId;
    @Column(name = "has_read")
    private int hasRead;
    @Column(name = "state")
    private int state;
    public Invitation(){}
    public Invitation(int id, int groupId, int userId, int inviterId, int hasRead, int state) {
        this.id=id;
        this.groupId = groupId;
        this.userId = userId;
        this.inviterId = inviterId;
        this.hasRead = hasRead;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getInviterId() {
        return inviterId;
    }

    public void setInviterId(int inviterId) {
        this.inviterId = inviterId;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

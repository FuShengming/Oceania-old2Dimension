package com.old2dimension.OCEANIA.po;

import javax.persistence.*;

@Entity
@Table(name = "team_member")
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "user_id")
    private  int userId;
    @Column(name = "is_leader")
    private int isLeader;
public GroupMember(){}
    public GroupMember(int groupId, int userId, int isLeader) {

        this.groupId = groupId;
        this.userId = userId;
        this.isLeader = isLeader;
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

    public int getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(int isLeader) {
        this.isLeader = isLeader;
    }
}

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
    private  int memberId;
    @Column(name = "is_leader")
    private int isLeader;

    public GroupMember(int groupId, int memberId, int isLeader) {

        this.groupId = groupId;
        this.memberId = memberId;
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

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(int isLeader) {
        this.isLeader = isLeader;
    }
}

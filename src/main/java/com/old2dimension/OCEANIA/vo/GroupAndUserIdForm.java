package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Group;

public class GroupAndUserIdForm {
    private Group group;
    private int userId;

    public GroupAndUserIdForm(){}

    public GroupAndUserIdForm(Group group, int userId) {
        this.group = group;
        this.userId = userId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}

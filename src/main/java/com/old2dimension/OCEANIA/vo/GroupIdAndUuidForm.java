package com.old2dimension.OCEANIA.vo;

public class GroupIdAndUuidForm {
    private int groupId;

    public GroupIdAndUuidForm(){}

    public GroupIdAndUuidForm(int groupId, String uuid) {
        this.groupId = groupId;
        this.uuid = uuid;
    }

    private String uuid;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}

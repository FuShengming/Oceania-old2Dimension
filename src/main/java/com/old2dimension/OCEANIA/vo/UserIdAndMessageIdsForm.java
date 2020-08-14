package com.old2dimension.OCEANIA.vo;

import java.util.List;

public class UserIdAndMessageIdsForm {
    private int userId;

    private List<Integer> messageIds;

    public UserIdAndMessageIdsForm() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<Integer> messageIds) {
        this.messageIds = messageIds;
    }

}

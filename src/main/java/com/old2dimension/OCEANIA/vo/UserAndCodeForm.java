package com.old2dimension.OCEANIA.vo;

public class UserAndCodeForm {
    private int userId;

    private int codeId;

    public UserAndCodeForm(int userId, int codeId) {
        this.userId = userId;
        this.codeId = codeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

}

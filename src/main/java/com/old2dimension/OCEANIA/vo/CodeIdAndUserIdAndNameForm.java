package com.old2dimension.OCEANIA.vo;

public class CodeIdAndUserIdAndNameForm {
    private int codeId;

    private int userId;

    private String name;

    public CodeIdAndUserIdAndNameForm(int codeId, int userId, String name) {
        this.codeId = codeId;
        this.userId = userId;
        this.name = name;
    }

    public CodeIdAndUserIdAndNameForm() {
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int id) {
        this.codeId = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

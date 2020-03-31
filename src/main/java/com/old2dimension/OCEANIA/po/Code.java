package com.old2dimension.OCEANIA.po;

public class Code {

    private int userId;

    private int codeId;

    private String name;

    public Code(int userId,int codeId,String name){
        this.userId=userId;
        this.codeId=codeId;
        this.name=name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

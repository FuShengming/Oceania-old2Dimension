package com.old2dimension.OCEANIA.po;

public class UserIdAndCodeMes {
    private int userId;

    private CodeMes codeMes;

    public UserIdAndCodeMes(){}

    public UserIdAndCodeMes(int userId, CodeMes codeMes) {
        this.userId = userId;
        this.codeMes = codeMes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CodeMes getCodeMes() {
        return codeMes;
    }

    public void setCodeMes(CodeMes codeMes) {
        this.codeMes = codeMes;
    }
}

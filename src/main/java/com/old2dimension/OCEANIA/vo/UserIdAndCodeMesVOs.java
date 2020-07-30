package com.old2dimension.OCEANIA.vo;

import java.util.ArrayList;

public class UserIdAndCodeMesVOs {
    private int userId;

    private ArrayList<CodeMesVO> codeMesVOs;

    public UserIdAndCodeMesVOs() {
    }

    public UserIdAndCodeMesVOs(int userId, ArrayList<CodeMesVO> codeMesVOs) {
        this.userId = userId;
        this.codeMesVOs = codeMesVOs;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<CodeMesVO> getCodeMesVOs() {
        return codeMesVOs;
    }

    public void setCodeMesVOs(ArrayList<CodeMesVO> codeMesVOs) {
        this.codeMesVOs = codeMesVOs;
    }
}

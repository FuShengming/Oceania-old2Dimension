package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.vo.CodeMesVO;

import java.util.ArrayList;

public class UserIdAndCodeMesesVO {
    private int userId;

    private ArrayList<CodeMesVO> codeMesesVO;

    public UserIdAndCodeMesesVO(){}

    public UserIdAndCodeMesesVO(int userId, CodeMesVO codeMesVO) {
        this.userId = userId;
        this.codeMesesVO = codeMesesVO;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<CodeMesVO> getCodeMesesVO() {
        return codeMesesVO;
    }

    public void setCodeMesVO(ArrayList<CodeMesVO> codeMesesVO) {
        this.codeMesesVO = codeMesesVO;
    }
}

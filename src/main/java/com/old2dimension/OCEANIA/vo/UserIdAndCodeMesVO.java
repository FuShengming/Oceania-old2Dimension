package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.vo.CodeMesVO;

public class UserIdAndCodeMesVO {
    private int userId;

    private CodeMesVO codeMesVO;

    public UserIdAndCodeMesVO(){}

    public UserIdAndCodeMesVO(int userId, CodeMesVO codeMesVO) {
        this.userId = userId;
        this.codeMesVO = codeMesVO;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CodeMesVO getCodeMesVO() {
        return codeMesVO;
    }

    public void setCodeMesVO(CodeMesVO codeMesVO) {
        this.codeMesVO = codeMesVO;
    }
}

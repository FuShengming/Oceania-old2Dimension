package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Code;

import java.util.Date;

public class CodeAndDateForm {
    private int codeId;

    private String codeName;
    private Date date;

    public CodeAndDateForm(int codeId, String codeName, Date date) {
        this.codeId = codeId;
        this.codeName = codeName;
        this.date = date;
    }

    public CodeAndDateForm(Code code, Date date) {
        this.codeId = code.getId();
        this.codeName = code.getName();
        this.date = date;
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}

package com.old2dimension.OCEANIA.vo;

import java.util.Date;

public class WorkSpaceVO {
    private int userId;

    private int codeId;
    private Date date;
    private double closeness;

    public WorkSpaceVO(int userId, int codeId, Date date, double closeness) {
        this.userId = userId;
        this.codeId = codeId;
        this.date = date;
        this.closeness = closeness;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCloseness() {
        return closeness;
    }

    public void setCloseness(double closeness) {
        this.closeness = closeness;
    }
}

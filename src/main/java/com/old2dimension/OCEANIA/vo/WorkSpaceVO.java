package com.old2dimension.OCEANIA.vo;

import java.util.Date;

public class WorkSpaceVO {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int userId;
    private int codeId;
    private Date date;
    private double closeness;
    private String cyInfo;

    public WorkSpaceVO(int userId, int codeId, Date date, double closeness, String cyInfo) {
        this.userId = userId;
        this.codeId = codeId;
        this.date = date;
        this.closeness = closeness;
        this.cyInfo = cyInfo;
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

    public String getCyInfo() {
        return cyInfo;
    }

    public void setCyInfo(String cyInfo) {
        this.cyInfo = cyInfo;
    }
}

package com.old2dimension.OCEANIA.po;

import com.old2dimension.OCEANIA.vo.WorkSpaceVO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "work_space")
public class WorkSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "user_id")
    private int userId;
    @Column(name = "code_id")
    private int codeId;
    @Column(name = "work_space_date")
    private Date date;
    @Column(name = "closeness")
    private double closeness;
    @Column(name = "cy_info")
    private String cyInfo;

    public WorkSpace(int userId, int codeId, Date date, double closeness) {
        this.userId = userId;
        this.codeId = codeId;
        this.date = date;
        this.closeness = closeness;
    }

    public WorkSpace(int id, int userId, int codeId, Date date, double closeness) {
        this.id = id;
        this.userId = userId;
        this.codeId = codeId;
        this.date = date;
        this.closeness = closeness;
    }

    public WorkSpace(int id, int userId, int codeId, Date date, double closeness, String cyInfo) {
        this.id = id;
        this.userId = userId;
        this.codeId = codeId;
        this.date = date;
        this.closeness = closeness;
        this.cyInfo = cyInfo;
    }


    public WorkSpace() {
    }

    public WorkSpace(WorkSpaceVO workSpaceVO) {
        this.userId = workSpaceVO.getUserId();
        this.id = workSpaceVO.getId();
        this.codeId = workSpaceVO.getCodeId();
        this.date = workSpaceVO.getDate();
        this.closeness = workSpaceVO.getCloseness();
        this.cyInfo = workSpaceVO.getCyInfo();
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

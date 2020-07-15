package com.old2dimension.OCEANIA.vo;

import java.util.Date;
import java.util.List;

public class TaskVO {
    private int id;
    private String name;
    private String label;
    private List<Integer> members_id;
    private Date startDate;
    private Date endDate;

    public TaskVO(int id, String name, String label, List<Integer> members_id, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.members_id = members_id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Integer> getMembers_id() {
        return members_id;
    }

    public void setMembers_id(List<Integer> members_id) {
        this.members_id = members_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

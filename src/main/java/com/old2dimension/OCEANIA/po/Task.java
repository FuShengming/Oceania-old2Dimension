package com.old2dimension.OCEANIA.po;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "team_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "name")
    private String name;
    @Column(name = "label")
    private String label;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "state")
    private int state;

    private Task(){}

    public Task(int id, int groupId,String name, String label, String description, Date startDate, Date endDate,int state) {
        this.id = id;
        this.groupId=groupId;
        this.name = name;
        this.label = label;
        this.description=description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state=state;
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

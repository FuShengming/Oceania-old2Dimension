package com.old2dimension.OCEANIA.po;

import javax.persistence.*;

@Entity
@Table(name = "team_task_assignment")
public class TaskAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "task_id")
    private int taskId;
    @Column(name = "user_id")
    private int userId;

    private TaskAssignment(){}

    public TaskAssignment(int groupId, int taskId, int userId) {
        this.groupId = groupId;
        this.taskId = taskId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

package com.old2dimension.OCEANIA.po;

import javax.persistence.*;

@Entity
@Table(name = "team_code")
public class GroupCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "group_id")
    private int group_id;
    @Column(name = "code_id")
    private int code_id;

    public GroupCode(int group_id, int code_id) {
        this.group_id = group_id;
        this.code_id = code_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getCode_id() {
        return code_id;
    }

    public void setCode_id(int code_id) {
        this.code_id = code_id;
    }

}

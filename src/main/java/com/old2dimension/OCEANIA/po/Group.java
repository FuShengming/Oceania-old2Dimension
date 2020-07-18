package com.old2dimension.OCEANIA.po;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "team")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "group_name")
    private String name;
    private List<Integer> members;
    private int leaderId;

    public Group(int id, String name, List<Integer> members, int leaderId) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.leaderId = leaderId;
    }

    public Group() {
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

    public List<Integer> getMembers() {
        return members;
    }

    public void setMembers(List<Integer> members) {
        this.members = members;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

}

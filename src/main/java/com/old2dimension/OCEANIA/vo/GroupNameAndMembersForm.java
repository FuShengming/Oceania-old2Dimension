package com.old2dimension.OCEANIA.vo;

import java.util.List;

public class GroupNameAndMembersForm {
    private List<Integer> memberIds;
    private String name;


    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

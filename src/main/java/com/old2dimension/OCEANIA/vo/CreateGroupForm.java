package com.old2dimension.OCEANIA.vo;

import java.util.List;

public class CreateGroupForm {

    private String name;

    private int creatorId;

    public CreateGroupForm(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

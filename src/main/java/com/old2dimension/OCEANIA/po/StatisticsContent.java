package com.old2dimension.OCEANIA.po;

import java.util.ArrayList;

public class StatisticsContent {
    private int numOfUser;
    private ArrayList<UserIdAndCodeMes> content;

    public StatisticsContent(){}

    public StatisticsContent(int numOfUser, ArrayList<UserIdAndCodeMes> content) {
        this.numOfUser = numOfUser;
        this.content = content;
    }

    public int getNumOfUser() {
        return numOfUser;
    }

    public void setNumOfUser(int numOfUser) {
        this.numOfUser = numOfUser;
    }

    public ArrayList<UserIdAndCodeMes> getContent() {
        return content;
    }

    public void setContent(ArrayList<UserIdAndCodeMes> content) {
        this.content = content;
    }
}

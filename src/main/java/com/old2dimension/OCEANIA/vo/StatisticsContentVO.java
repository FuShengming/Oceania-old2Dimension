package com.old2dimension.OCEANIA.vo;

import java.util.ArrayList;

public class StatisticsContentVO {
    private int numOfUser;
    private ArrayList<UserIdAndCodeMesVOs> content;

    public StatisticsContentVO() {
    }

    public StatisticsContentVO(int numOfUser, ArrayList<UserIdAndCodeMesVOs> content) {
        this.numOfUser = numOfUser;
        this.content = content;
    }

    public int getNumOfUser() {
        return numOfUser;
    }

    public void setNumOfUser(int numOfUser) {
        this.numOfUser = numOfUser;
    }

    public ArrayList<UserIdAndCodeMesVOs> getContent() {
        return content;
    }

    public void setContent(ArrayList<UserIdAndCodeMesVOs> content) {
        this.content = content;
    }
}

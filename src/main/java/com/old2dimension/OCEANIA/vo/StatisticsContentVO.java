package com.old2dimension.OCEANIA.vo;

import java.util.ArrayList;

public class StatisticsContentVO {
    private int numOfUser;
    private ArrayList<UserIdAndCodeMesesVO> content;

    public StatisticsContentVO(){}

    public StatisticsContentVO(int numOfUser, ArrayList<UserIdAndCodeMesesVO> content) {
        this.numOfUser = numOfUser;
        this.content = content;
    }

    public int getNumOfUser() {
        return numOfUser;
    }

    public void setNumOfUser(int numOfUser) {
        this.numOfUser = numOfUser;
    }

    public ArrayList<UserIdAndCodeMesesVO> getContent() {
        return content;
    }

    public void setContent(ArrayList<UserIdAndCodeMesesVO> content) {
        this.content = content;
    }
}

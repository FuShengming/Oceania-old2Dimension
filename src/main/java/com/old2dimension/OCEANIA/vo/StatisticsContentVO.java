package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.vo.UserIdAndCodeMesVO;

import java.util.ArrayList;

public class StatisticsContentVO {
    private int numOfUser;
    private ArrayList<UserIdAndCodeMesVO> content;

    public StatisticsContentVO(){}

    public StatisticsContentVO(int numOfUser, ArrayList<UserIdAndCodeMesVO> content) {
        this.numOfUser = numOfUser;
        this.content = content;
    }

    public int getNumOfUser() {
        return numOfUser;
    }

    public void setNumOfUser(int numOfUser) {
        this.numOfUser = numOfUser;
    }

    public ArrayList<UserIdAndCodeMesVO> getContent() {
        return content;
    }

    public void setContent(ArrayList<UserIdAndCodeMesVO> content) {
        this.content = content;
    }
}

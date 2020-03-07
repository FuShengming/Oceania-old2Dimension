package com.old2dimension.OCEANIA.vo;

import java.util.ArrayList;

public class FindPathVO {

    public int getPathNum() {
        return pathNum;
    }

    /*
     * 建议直接用PathVO的ArrayList
     * 这个类可能会删除
     */
    private int pathNum;

    private ArrayList<PathVO> pathVOS;

    public void setPathNum(int pathNum) {
        this.pathNum = pathNum;
    }

    public void setPathVOS(ArrayList<PathVO> pathVOS) {
        this.pathVOS = pathVOS;
    }

    public ArrayList<PathVO> getPathVOS() {
        return pathVOS;
    }

    public FindPathVO() {
        pathVOS = new ArrayList<>();
    }

    public String toString() {
        String s = "";
        for (PathVO p : pathVOS) {
            s += p.toString();
        }
        return s;
    }
}

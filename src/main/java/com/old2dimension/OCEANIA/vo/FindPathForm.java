package com.old2dimension.OCEANIA.vo;

import java.util.ArrayList;

public class FindPathForm {
    private int pathNum;
    private ArrayList<PathForm> pathForms;

    public FindPathForm() {
        pathForms = new ArrayList<>();
    }

    public String toString() {
        String s = "";
        for (PathForm p : pathForms) {
            s += p.toString();
        }
        return s;
    }
}

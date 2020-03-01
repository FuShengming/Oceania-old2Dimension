package com.old2dimension.OCEANIA.vo;

import java.util.ArrayList;

public class FuncInfoForm {
    String belongPackage;
    String belongClass;
    String funcName;
    ArrayList<String> args;
    int id;


    public String getBelongPackage() {
        return belongPackage;
    }

    public void setBelongPackage(String belongPackage) {
        this.belongPackage = belongPackage;
    }

    public String getBelongClass() {
        return belongClass;
    }

    public void setBelongClass(String belongClass) {
        this.belongClass = belongClass;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

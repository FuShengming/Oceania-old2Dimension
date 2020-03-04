package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Vertex;

import java.util.ArrayList;

public class FuncInfoForm {
    String belongPackage;
    String belongClass;
    String funcName;
    String[] args;
    int id;

    public FuncInfoForm() {

    }

    public FuncInfoForm(Vertex vertex) {
        this.belongPackage = vertex.getBelongPackage();
        this.belongClass = vertex.getBelongClass();
        this.funcName = vertex.getFuncName();
        this.args = vertex.getArgs();
        this.id = vertex.getId();
    }

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

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

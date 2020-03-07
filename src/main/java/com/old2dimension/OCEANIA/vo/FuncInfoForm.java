package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Vertex;

import java.util.ArrayList;

public class FuncInfoForm {
    private String belongPackage;
    private String belongClass;
    private String funcName;
    private String[] args;
    private int id;

    public FuncInfoForm() {

    }

    FuncInfoForm(Vertex vertex) {
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

    public String getClassNameAndFunc(){
        String res=getBelongClass()+":"+getFuncName()+"(";
        for(int i = 0; i<args.length ; i++){
            if(i!=args.length-1){res += (args[i]+", ");}
            else{ res += (args[i]+")");}
        }
        return res;
    }
    @Override
    public boolean equals(Object obj){
        if(this ==obj){//如果是引用同一个实例
            return true;
        } if (obj != null && obj instanceof FuncInfoForm) {
            FuncInfoForm u = (FuncInfoForm) obj;
            return this.id==u.id;
        }else{
            return false;
        }
    }
}

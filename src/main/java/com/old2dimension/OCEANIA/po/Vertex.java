package com.old2dimension.OCEANIA.po;

public class Vertex {
    private String belongPackage;
    private String belongClass;
    private String funcName;
    private String[] args;
    private int id;
    private int inDegree;
    private int outDegree;

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

    public int getInDegree() {
        return inDegree;
    }

    public void setInDegree(int inDegree) {
        this.inDegree = inDegree;
    }

    public int getOutDegree() {
        return outDegree;
    }

    public void setOutDegree(int outDegree) {
        this.outDegree = outDegree;
    }


    public String getClassNameAndFunc() {
        return getBelongClass() + ":" + getFuncName() + "(" + getArgsString(args) + ")";
    }

    public String getFullName() {
        return belongPackage + "." + getBelongClass() + ":" + getFuncName() + "(" + getArgsString(args) + ")";
    }

    public String getArgsString(String[] args) {
        String res = "";
        for (int i = 0; i < args.length; i++) {
            if (i != args.length - 1) {
                res += (args[i] + ", ");
            } else {
                res += (args[i]);
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vertex)) return false;
        return ((Vertex) obj).getId() == this.getId();
    }
}

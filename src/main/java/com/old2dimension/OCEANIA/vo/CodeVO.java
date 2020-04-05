package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Code;

public class CodeVO {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private int userId;

    private String name;

    private int numOfVertices;

    private int numOfEdges;

    private int numOfDomains;

    private int is_default;

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    public void setNumOfVertices(int numOfVertices) {
        this.numOfVertices = numOfVertices;
    }

    public int getNumOfEdges() {
        return numOfEdges;
    }

    public void setNumOfEdges(int numOfEdges) {
        this.numOfEdges = numOfEdges;
    }

    public int getNumOfDomains() {
        return numOfDomains;
    }

    public void setNumOfDomains(int numOfDomains) {
        this.numOfDomains = numOfDomains;
    }

    public CodeVO(int userId, int codeId, String name, int numOfVertices, int numOfEdges, int numOfDomains,int is_default) {
        this.userId = userId;
        this.name = name;
        this.numOfVertices = numOfVertices;
        this.numOfEdges = numOfEdges;
        this.numOfDomains = numOfDomains;
        this.is_default=is_default;
    }

    public CodeVO(Code c){
        this.id=c.getId();
        this.is_default=c.getIs_default();
        this.name=c.getName();
        this.numOfDomains=c.getNumOfDomains();
        this.numOfEdges=c.getNumOfEdges();
        this.numOfDomains=c.getNumOfDomains();
        this.userId=c.getUserId();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

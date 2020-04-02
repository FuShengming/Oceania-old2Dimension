package com.old2dimension.OCEANIA.vo;

public class CodeVO {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private int userId;

    private int codeId;

    private String name;

    private int numOfVertices;

    private int numOfEdges;

    private int numOfDomains;

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

    public CodeVO(int userId, int codeId, String name, int numOfVertices, int numOfEdges, int numOfDomains) {
        this.userId = userId;
        this.codeId = codeId;
        this.name = name;
        this.numOfVertices = numOfVertices;
        this.numOfEdges = numOfEdges;
        this.numOfDomains = numOfDomains;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

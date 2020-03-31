package com.old2dimension.OCEANIA.vo;

public class DomainLabelVO {
    private int userId;

    private int codeId;

    private int firstEdgeId;

    private int numOfVertex;

    private String content;

    public DomainLabelVO(int userId, int codeId, int firstEdgeId, int numOfVertex, String content) {
        this.userId = userId;
        this.codeId = codeId;
        this.firstEdgeId = firstEdgeId;
        this.numOfVertex = numOfVertex;
        this.content = content;
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

    public int getFirstEdgeId() {
        return firstEdgeId;
    }

    public void setFirstEdgeId(int firstEdgeId) {
        this.firstEdgeId = firstEdgeId;
    }

    public int getNumOfVertex() {
        return numOfVertex;
    }

    public void setNumOfVertex(int numOfVertex) {
        this.numOfVertex = numOfVertex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

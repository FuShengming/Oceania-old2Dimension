package com.old2dimension.OCEANIA.po;

public class VertexLabel {

    private int userId;

    private int codeId;

    private int vertexId;

    private String content;

    public VertexLabel(int userId, int codeId, int vertexId, String content) {
        this.userId = userId;
        this.codeId = codeId;
        this.vertexId = vertexId;
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

    public int getVertexId() {
        return vertexId;
    }

    public void setVertexId(int vertexId) {
        this.vertexId = vertexId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
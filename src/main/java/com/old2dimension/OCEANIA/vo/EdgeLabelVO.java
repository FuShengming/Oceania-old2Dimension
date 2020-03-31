package com.old2dimension.OCEANIA.vo;

public class EdgeLabelVO {

    private int userId;

    private int edgeId;

    private int codeId;

    private String content;

    public EdgeLabelVO(int userId, int edgeId, int codeId, String content) {
        this.userId = userId;
        this.edgeId = edgeId;
        this.codeId = codeId;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(int edgeId) {
        this.edgeId = edgeId;
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

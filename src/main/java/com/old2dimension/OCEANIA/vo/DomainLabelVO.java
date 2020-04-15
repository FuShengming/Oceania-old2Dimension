package com.old2dimension.OCEANIA.vo;

import java.util.Objects;

public class DomainLabelVO {
    public DomainLabelVO() {
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int userId;

    private int codeId;

    private int firstEdgeId;

    private int numOfVertex;

    private String title;

    private String content;

    public DomainLabelVO(int userId, int codeId, int firstEdgeId, int numOfVertex, String title, String content) {
        this.userId = userId;
        this.codeId = codeId;
        this.firstEdgeId = firstEdgeId;
        this.numOfVertex = numOfVertex;
        this.title = title;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public String getTitle() {
        return title;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }

    public int getCodeId() {
        return codeId;
    }

//    public void setCodeId(int codeId) {
//        this.codeId = codeId;
//    }

    public int getFirstEdgeId() {
        return firstEdgeId;
    }

//    public void setFirstEdgeId(int firstEdgeId) {
//        this.firstEdgeId = firstEdgeId;
//    }

    public int getNumOfVertex() {
        return numOfVertex;
    }

//    public void setNumOfVertex(int numOfVertex) {
//        this.numOfVertex = numOfVertex;
//    }

    public String getContent() {
        return content;
    }

//    public void setContent(String content) {
//        this.content = content;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainLabelVO that = (DomainLabelVO) o;
        return id == that.id &&
                userId == that.userId &&
                codeId == that.codeId &&
                firstEdgeId == that.firstEdgeId &&
                numOfVertex == that.numOfVertex &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, codeId, firstEdgeId, numOfVertex, title, content);
    }
}

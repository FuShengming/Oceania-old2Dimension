package com.old2dimension.OCEANIA.vo;

import java.util.Objects;

public class EdgeLabelVO {

    public EdgeLabelVO() {
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int userId;

    private int edgeId;

    private int codeId;

    public String getTitle() {
        return title;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }

    private String title;

    private String content;

    public EdgeLabelVO(int userId, int edgeId, int codeId, String title, String content) {
        this.userId = userId;
        this.edgeId = edgeId;
        this.codeId = codeId;
        this.title = title;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public int getEdgeId() {
        return edgeId;
    }

//    public void setEdgeId(int edgeId) {
//        this.edgeId = edgeId;
//    }

    public int getCodeId() {
        return codeId;
    }

//    public void setCodeId(int codeId) {
//        this.codeId = codeId;
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
        EdgeLabelVO that = (EdgeLabelVO) o;
        return id == that.id &&
                userId == that.userId &&
                edgeId == that.edgeId &&
                codeId == that.codeId &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, edgeId, codeId, title, content);
    }
}

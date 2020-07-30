package com.old2dimension.OCEANIA.vo;

import java.util.Objects;

public class VertexLabelVO {
    public VertexLabelVO() {
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

    private int vertexId;

    private String title;

    private String content;

    public String getTitle() {
        return title;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }

    public VertexLabelVO(int userId, int codeId, int vertexId, String title, String content) {
        this.userId = userId;
        this.codeId = codeId;
        this.vertexId = vertexId;
        this.content = content;
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public int getCodeId() {
        return codeId;
    }

//    public void setCodeId(int codeId) {
//        this.codeId = codeId;
//    }

    public int getVertexId() {
        return vertexId;
    }

//    public void setVertexId(int vertexId) {
//        this.vertexId = vertexId;
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
        VertexLabelVO that = (VertexLabelVO) o;
        return id == that.id &&
                userId == that.userId &&
                codeId == that.codeId &&
                vertexId == that.vertexId &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, codeId, vertexId, title, content);
    }
}

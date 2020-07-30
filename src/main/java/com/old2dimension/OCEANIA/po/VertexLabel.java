package com.old2dimension.OCEANIA.po;

import javax.persistence.*;

@Entity
@Table(name = "vertex_label")
public class VertexLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "user_id")
    private int userId;

    @Column(name = "code_id")
    private int codeId;

    @Column(name = "vertex_id")
    private int vertexId;

    @Column(name = "content")
    private String content;

    @Column(name = "title")
    private String title;

    public VertexLabel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VertexLabel(int userId, int codeId, int vertexId, String title, String content) {
        this.userId = userId;
        this.codeId = codeId;
        this.vertexId = vertexId;
        this.content = content;
        this.title = title;
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

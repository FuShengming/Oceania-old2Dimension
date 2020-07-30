package com.old2dimension.OCEANIA.po;

import javax.persistence.*;

@Entity
@Table(name = "edge_label")
public class EdgeLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "title")
    private String title;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "code_id")
    private int codeId;

    @Column(name = "edge_id")
    private int edgeId;

    @Column(name = "content")
    private String content;

    public EdgeLabel(int userId, int edgeId, int codeId, String title, String content) {
        this.userId = userId;
        this.edgeId = edgeId;
        this.codeId = codeId;
        this.title = title;
        this.content = content;
    }

    public EdgeLabel() {
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

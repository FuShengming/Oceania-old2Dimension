package com.old2dimension.OCEANIA.po;

import javax.persistence.*;

@Entity
@Table(name = "domain_label")
public class DomainLabel {
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

    @Column(name = "first_edge_id")
    private int firstEdgeId;

    @Column(name = "num_of_vertex")
    private int numOfVertex;

    @Column(name = "content")
    private String content;

    public DomainLabel(int userId, int codeId, int firstEdgeId, int numOfVertex, String content) {
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

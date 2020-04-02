package com.old2dimension.OCEANIA.po;

import javax.persistence.*;

@Entity
@Table(name = "code")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name="user_id")
    private int userId;

    @Column(name="codeId")
    private int codeId;
    @Column(name="name")
    private String name;
    @Column(name="numOfVertices")
    private int numOfVertices;
    @Column(name="numOfEdges")
    private int numOfEdges;
    @Column(name="numOfDomains")
    private int numOfDomains;

    public Code(int userId, int codeId, String name, int numOfVertices, int numOfEdges, int numOfDomains) {
        this.userId=userId;
        this.codeId = codeId;
        this.name = name;
        this.numOfVertices = numOfVertices;
        this.numOfEdges = numOfEdges;
        this.numOfDomains = numOfDomains;
    }

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

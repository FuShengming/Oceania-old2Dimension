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

    @Column(name = "user_id")
    private int userId;

    @Column(name = "name")
    private String name;
    @Column(name = "num_of_vertices")
    private int numOfVertices;
    @Column(name = "num_of_edges")
    private int numOfEdges;
    @Column(name = "num_of_domains")
    private int numOfDomains;
    @Column(name = "is_default")
    private int is_default;


    public Code() {
    }


    public Code(int userId, String name, int numOfVertices, int numOfEdges, int numOfDomains, int is_default) {

        this.userId = userId;

        this.name = name;
        this.numOfVertices = numOfVertices;
        this.numOfEdges = numOfEdges;
        this.numOfDomains = numOfDomains;
        this.is_default = is_default;
    }


    public Code(int id, int userId, String name, int numOfVertices, int numOfEdges, int numOfDomains, int is_default) {

        this.userId = userId;
        this.id = id;
        this.name = name;
        this.numOfVertices = numOfVertices;
        this.numOfEdges = numOfEdges;
        this.numOfDomains = numOfDomains;
        this.is_default = is_default;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

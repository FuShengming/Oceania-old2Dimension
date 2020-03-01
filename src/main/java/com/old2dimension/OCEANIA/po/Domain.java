package com.old2dimension.OCEANIA.po;

import java.util.ArrayList;

public class Domain {
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Domain(int verticesNum) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }
}

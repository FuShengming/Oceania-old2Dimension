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

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }


    public Domain(ArrayList<Vertex> vertexes,ArrayList<Edge> edges) {
        setEdges(edges);
        setVertices(vertexes);
    }
}

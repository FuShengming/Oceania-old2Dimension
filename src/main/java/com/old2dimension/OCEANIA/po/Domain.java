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

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void addEdges(Edge edge) {
        this.edges.add(edge);
    }

    public Domain(ArrayList<Vertex> vertexes, ArrayList<Edge> edges) {
        setEdges(edges);
        setVertices(vertexes);
    }

    public boolean contains(Edge e) {
        for (Edge edge : this.edges) {
            if (edge.equals(e)) return true;
        }
        return false;
    }

    public boolean contains(Vertex v) {
        for (Vertex vertex : this.vertices) {
            if (vertex.equals(v)) return true;
        }
        return false;
    }
}

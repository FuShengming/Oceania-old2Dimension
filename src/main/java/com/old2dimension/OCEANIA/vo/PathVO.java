package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Edge;

import java.util.ArrayList;

public class PathVO {
    private ArrayList<Edge> edges;

    public PathVO() {
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public String toString() {
        return "";
    }
}

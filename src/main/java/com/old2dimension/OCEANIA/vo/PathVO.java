package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Edge;

import java.util.ArrayList;

public class PathVO {
    public ArrayList<Edge> getEdges() {
        return edges;
    }

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


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PathVO)) return false;
        if (((PathVO) obj).edges.size() != edges.size()) {
            return false;
        }
        for (int i = 0; i < edges.size(); i++) {
            if (!edges.get(i).equals(((PathVO) obj).edges.get(i))) {
                return false;
            }
        }
        return true;
    }
}

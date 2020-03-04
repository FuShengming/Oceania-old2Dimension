package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Domain;
import com.old2dimension.OCEANIA.po.Edge;

import java.util.ArrayList;

public class DomainVO {
    private int verticesNum;
    private ArrayList<EdgeVO> edgeVOS;
    private int id;

    public DomainVO() {

    }

    public DomainVO(Domain domain) {
        this.verticesNum = domain.getVertices().size();
        this.edgeVOS = new ArrayList<>();
        for (Edge edge : domain.getEdges()) {
            edgeVOS.add(new EdgeVO(edge));
        }
        this.id = domain.getId();
    }

    public int getVerticesNum() {
        return verticesNum;
    }

    public void setVerticesNum(int verticesNum) {
        this.verticesNum = verticesNum;
    }

    public ArrayList<EdgeVO> getEdgeVOS() {
        return edgeVOS;
    }

    public void setEdgeVOS(ArrayList<EdgeVO> edgeVOS) {
        this.edgeVOS = edgeVOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

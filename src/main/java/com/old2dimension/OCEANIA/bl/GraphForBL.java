package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.AdjacencyMatrix;
import com.old2dimension.OCEANIA.po.Edge;
import com.old2dimension.OCEANIA.po.Vertex;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface GraphForBL {

    public AdjacencyMatrix getAdjacencyMatrix();

    public ArrayList<Vertex> getAllVertexes();

    public ArrayList<Edge> getAllEdges();
}

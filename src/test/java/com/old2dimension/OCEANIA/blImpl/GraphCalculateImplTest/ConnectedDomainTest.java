package com.old2dimension.OCEANIA.blImpl.GraphCalculateImplTest;

import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.DomainSetVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.WeightForm;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

class ConnectedDomainTest {

    private static ArrayList<Edge> edges;
    private static GraphCalculateImpl graphCalculate;
    private static ArrayList<WeightForm> weights;
    private static AdjacencyMatrix adjacencyMatrix;

    @BeforeAll
    static void initialize() {
        /* Use case
                v0 → v1
                 ↓    ↓
           v2 → v3 → v4
            ↑    ↓
           v5 ← v6
           edge start   end closeness
           e0   v0      v1  2/3
           e1   v0      v3  2/4
           e2   v1      v4  2/3
           e3   v2      v3  2/3
           e4   v3      v4  2/4
           e5   v3      v6  2/3
           e6   v5      v2  2/2
           e7   v6      v5  2/2
        */
        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            Vertex vertex = new Vertex();
            vertex.setId(i);
            vertices.add(vertex);
        }
        edges = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            Edge edge = new Edge();
            edge.setId(i);
            edges.add(edge);
        }
        edges.get(0).setStart(vertices.get(0));
        edges.get(0).setEnd(vertices.get(1));
        edges.get(0).addWeight(new Closeness(2.0 / 3));
        edges.get(1).setStart(vertices.get(0));
        edges.get(1).setEnd(vertices.get(3));
        edges.get(1).addWeight(new Closeness(2.0 / 4));
        edges.get(2).setStart(vertices.get(1));
        edges.get(2).setEnd(vertices.get(4));
        edges.get(2).addWeight(new Closeness(2.0 / 3));
        edges.get(3).setStart(vertices.get(2));
        edges.get(3).setEnd(vertices.get(3));
        edges.get(3).addWeight(new Closeness(2.0 / 3));
        edges.get(4).setStart(vertices.get(3));
        edges.get(4).setEnd(vertices.get(4));
        edges.get(4).addWeight(new Closeness(2.0 / 4));
        edges.get(5).setStart(vertices.get(3));
        edges.get(5).setEnd(vertices.get(6));
        edges.get(5).addWeight(new Closeness(2.0 / 3));
        edges.get(6).setStart(vertices.get(5));
        edges.get(6).setEnd(vertices.get(2));
        edges.get(6).addWeight(new Closeness(2.0 / 2));
        edges.get(7).setStart(vertices.get(6));
        edges.get(7).setEnd(vertices.get(5));
        edges.get(7).addWeight(new Closeness(2.0 / 2));
        graphCalculate = new GraphCalculateImpl();
        graphCalculate.allVertexes = vertices;
        graphCalculate.allEdges = edges;
        adjacencyMatrix = new AdjacencyMatrix(7);
        adjacencyMatrix.setMatrix(0, 1, true);
        adjacencyMatrix.setMatrix(0, 3, true);
        adjacencyMatrix.setMatrix(1, 4, true);
        adjacencyMatrix.setMatrix(2, 3, true);
        adjacencyMatrix.setMatrix(3, 4, true);
        adjacencyMatrix.setMatrix(3, 6, true);
        adjacencyMatrix.setMatrix(5, 2, true);
        adjacencyMatrix.setMatrix(6, 5, true);
        graphCalculate.adMatrix = adjacencyMatrix;
        weights = new ArrayList<>();
        weights.add(new WeightForm("closeness", 0.55));
    }

    @Test
    void getConnectedDomains1() {
        ResponseVO responseVO = graphCalculate.getConnectedDomains(new ArrayList<>());
        Assert.assertEquals(1, ((DomainSetVO) responseVO.getContent()).getDomainVOs().size());
        Assert.assertEquals(7, ((DomainSetVO) responseVO.getContent()).getDomainVOs().get(0).getVerticesNum());
    }

    @Test
    void getConnectedDomains2() {
        ResponseVO responseVO = graphCalculate.getConnectedDomains(weights);
        Assert.assertEquals(2, ((DomainSetVO) responseVO.getContent()).getDomainVOs().size());
        Assert.assertEquals(3, ((DomainSetVO) responseVO.getContent()).getDomainVOs().get(0).getVerticesNum());
        Assert.assertEquals(4, ((DomainSetVO) responseVO.getContent()).getDomainVOs().get(1).getVerticesNum());
    }
}
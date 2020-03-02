package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.po.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GraphCalculateImplTest {

    private static ArrayList<Edge> edges;
    private static GraphCalculateImpl graphCalculate;

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
    }

    @Test
    void filterByWeights1() {
        DomainSet domainSet = graphCalculate.filterByWeights(edges, new ArrayList<>());
        Assert.assertEquals(1, domainSet.getDomainSetSize());
        Assert.assertEquals(7, domainSet.getDomains().get(0).getVertices().size());
        Assert.assertEquals(8, domainSet.getDomains().get(0).getEdges().size());
    }

    @Test
    void filterByWeights2() {
        ArrayList<Weight> weights = new ArrayList<>();
        weights.add(new Closeness(0.6));
        DomainSet domainSet = graphCalculate.filterByWeights(edges, weights);
        Assert.assertEquals(2, domainSet.getDomainSetSize());
    }
}
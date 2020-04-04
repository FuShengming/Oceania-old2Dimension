package com.old2dimension.OCEANIA.blImpl.PathBLImplTest;

import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.blImpl.PathBLImpl;
import com.old2dimension.OCEANIA.po.AdjacencyMatrix;
import com.old2dimension.OCEANIA.po.Edge;
import com.old2dimension.OCEANIA.po.Vertex;
import com.old2dimension.OCEANIA.vo.FindPathVO;
import com.old2dimension.OCEANIA.vo.VertexVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;


import java.util.ArrayList;


public class PathBLImplTest {

    private static ArrayList<Edge> edges;
    private static PathBLImpl pathBL;

    @BeforeAll
    public static void initialize() {

        /*
        * V4  ←  V1   →   V5  →  V0
        *               ↗      ↙
        *        ↓    ↗      ↙
        *           ↗      ↙
        *        V2   →   V3  →  V6
        * */

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
        edges.get(0).setStart(vertices.get(1));
        edges.get(0).setEnd(vertices.get(2));
        edges.get(1).setStart(vertices.get(2));
        edges.get(1).setEnd(vertices.get(3));
        edges.get(2).setStart(vertices.get(3));
        edges.get(2).setEnd(vertices.get(6));
        edges.get(3).setStart(vertices.get(1));
        edges.get(3).setEnd(vertices.get(4));
        edges.get(4).setStart(vertices.get(0));
        edges.get(4).setEnd(vertices.get(3));
        edges.get(5).setStart(vertices.get(2));
        edges.get(5).setEnd(vertices.get(5));
        edges.get(6).setStart(vertices.get(5));
        edges.get(6).setEnd(vertices.get(0));
        edges.get(7).setStart(vertices.get(1));
        edges.get(7).setEnd(vertices.get(5));

        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(7);
        adjacencyMatrix.setMatrix(1, 2, true);
        adjacencyMatrix.setMatrix(2, 3, true);
        adjacencyMatrix.setMatrix(3, 6, true);
        adjacencyMatrix.setMatrix(1, 4, true);
        adjacencyMatrix.setMatrix(0, 3, true);
        adjacencyMatrix.setMatrix(2, 5, true);
        adjacencyMatrix.setMatrix(5, 0, true);
        adjacencyMatrix.setMatrix(1, 5, true);
        GraphCalculateImpl graphCalculate = new GraphCalculateImpl();
        graphCalculate.allEdges = edges;
        graphCalculate.allVertexes = vertices;
        graphCalculate.adMatrix = adjacencyMatrix;

        pathBL = new PathBLImpl(graphCalculate);
    }

    @Test
    public void findPath1() throws Exception {
        Vertex v1 = new Vertex();
        v1.setId(1);
        Vertex v3 = new Vertex();
        v3.setId(3);
        VertexVO f1 = new VertexVO(v1);
        VertexVO f3 = new VertexVO(v3);
        FindPathVO paths = (FindPathVO)pathBL.findPath(f1, f3).getContent();
        Assert.assertEquals(3, paths.getPathNum());
    }

    @Test
    public void findPath2() throws Exception {
        Vertex v1 = new Vertex();
        v1.setId(1);
        Vertex v3 = new Vertex();
        v3.setId(0);
        VertexVO f1 = new VertexVO(v1);
        VertexVO f3 = new VertexVO(v3);
        FindPathVO paths = (FindPathVO)pathBL.findPath(f1, f3).getContent();
        Assert.assertEquals(2, paths.getPathNum());
    }

    @Test
    public void findPath3() throws Exception {
        Vertex v1 = new Vertex();
        v1.setId(2);
        Vertex v3 = new Vertex();
        v3.setId(4);
        VertexVO f1 = new VertexVO(v1);
        VertexVO f3 = new VertexVO(v3);
        FindPathVO paths = (FindPathVO)pathBL.findPath(f1, f3).getContent();
        Assert.assertEquals(0, paths.getPathNum());
    }
}
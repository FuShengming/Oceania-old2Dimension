package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.GraphForBL;
import com.old2dimension.OCEANIA.bl.PathBL;
import com.old2dimension.OCEANIA.po.AdjacencyMatrix;
import com.old2dimension.OCEANIA.po.DomainSet;
import com.old2dimension.OCEANIA.po.Edge;
import com.old2dimension.OCEANIA.po.Vertex;
import com.old2dimension.OCEANIA.vo.FindPathVO;
import com.old2dimension.OCEANIA.vo.FuncInfoForm;
import com.old2dimension.OCEANIA.vo.PathVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class PathBLImpl implements PathBL {
    private final GraphForBL graphForBL;
    private final AdjacencyMatrix adjacencyMatrix;
    private final ArrayList<Vertex> vertexes;
    private final ArrayList<Edge> edges;
    private boolean[] visited;
    private ArrayList<Edge> stack;

    public PathBLImpl(GraphForBL graphForBL) {
        this.graphForBL = graphForBL;
        this.adjacencyMatrix = graphForBL.getAdjacencyMatrix();
        this.vertexes = graphForBL.getAllVertexes();
        this.edges = graphForBL.getAllEdges();
    }

    @Override
    public FindPathVO findPath(FuncInfoForm startVertex, FuncInfoForm endVertex) {
        visited = new boolean[adjacencyMatrix.getVerticesNum()];
        stack = new ArrayList<>();
        Vertex start = null, end = null;
        for (Vertex v : vertexes) {
            if (v.getId() == startVertex.getId()) {
                start = v;
            }
            if (v.getId() == endVertex.getId()) {
                end = v;
            }
        }
        ArrayList<PathVO> result = findPathDFS(start, end);
        FindPathVO findPathVO = new FindPathVO();
        findPathVO.setPathVOS(result);
        findPathVO.setPathNum(result.size());
        return findPathVO;
    }

    private ArrayList<PathVO> findPathDFS(Vertex start, Vertex end) {
        visited[start.getId()] = true;
        ArrayList<PathVO> paths = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.getVerticesNum(); i++) {
            if (adjacencyMatrix.getMatrix(start.getId(), i) && !visited[i]) {
                stack.add(getEdgeByVertex(start, vertexes.get(i)));
                if (i == end.getId()) {
                    PathVO p = new PathVO();
                    for (Edge e : stack) {
                        p.addEdge(e);
                    }
                    paths.add(p);
                }
                else {
                    paths.addAll(findPathDFS(vertexes.get(i), end));
                }
                pop(stack);
            }
        }
        return paths;
    }

    private Edge getEdgeByVertex(Vertex start, Vertex end) {
        for (Edge e : edges) {
            if (e.getStart().getId() == start.getId() && e.getEnd().getId() == end.getId()) {
                return e;
            }
        }
        return null;
    }

    private ArrayList<PathVO> integrate(PathVO path, ArrayList<PathVO> pathList) {
        if (path == null) {
            return pathList;
        }
        if (pathList == null) {
            ArrayList<PathVO> p = new ArrayList<>();
            p.add(path);
            return p;
        }
        pathList.add(path);
        return pathList;
    }

    private ArrayList<Edge> pop(ArrayList<Edge> paths) {
        if (paths.size() == 0) {
            return paths;
        }
        paths.remove(paths.size() - 1);
        return paths;
    }

}

package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.GraphCaculate;
import com.old2dimension.OCEANIA.bl.GraphForBL;
import com.old2dimension.OCEANIA.bl.PathBL;
import com.old2dimension.OCEANIA.po.AdjacencyMatrix;
import com.old2dimension.OCEANIA.po.DomainSet;
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
    private final boolean[] visited;
    private ArrayList<Integer> stack;

    public PathBLImpl(GraphForBL graphForBL) {
        this.graphForBL = graphForBL;
        this.adjacencyMatrix = graphForBL.getAdjacencyMatrix();
        this.visited = new boolean[adjacencyMatrix.getVerticesNum()];
    }

    @Override
    public FindPathVO findPath(FuncInfoForm startVertex, FuncInfoForm endVertex) {
        findPathDFS(startVertex.getId(), endVertex.getId());


        return null;
    }

    private PathVO findPathDFS(int startId, int endId) {
        stack.add(startId);
        visited[startId] = true;
        for (int i = 0; i < adjacencyMatrix.getVerticesNum(); i++) {
            if (adjacencyMatrix.getMatrix(startId, i) && !visited[i]) {
                if (i == endId) {
                    // return
                }
                return findPathDFS(i, endId);
            }
        }
        return null;
    }

}

package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.PathBL;
import com.old2dimension.OCEANIA.po.AdjacencyMatrix;
import com.old2dimension.OCEANIA.po.Edge;
import com.old2dimension.OCEANIA.po.Vertex;
import com.old2dimension.OCEANIA.vo.FindPathVO;
import com.old2dimension.OCEANIA.vo.VertexVO;
import com.old2dimension.OCEANIA.vo.PathVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PathBLImpl implements PathBL {
    private final AdjacencyMatrix adjacencyMatrix; // 邻接矩阵
    private final ArrayList<Vertex> vertexes; // 顶点集合
    private final ArrayList<Edge> edges; // 边集合
    private boolean[] visited; // 记录顶点是否已经被访问过的数组
    private ArrayList<Edge> stack; // 堆栈，用于存储访问过的边

    public PathBLImpl(GraphCalculateImpl graphCalculate) {
        this.adjacencyMatrix = graphCalculate.adMatrix;
        this.vertexes = graphCalculate.allVertexes;
        this.edges = graphCalculate.allEdges;
    }

    @Override
    public ResponseVO findPath(VertexVO startVertex, VertexVO endVertex) {
        // --- 初始化数组和栈 ---
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

        // --- 使用深度遍历查找路径 ---
        try {
            ArrayList<PathVO> result = findPathDFS(start, end);
            FindPathVO findPathVO = new FindPathVO();
            findPathVO.setPathVOS(result);
            findPathVO.setPathNum(result.size());
            return ResponseVO.buildSuccess(findPathVO);
        } catch (Exception e) {
            return ResponseVO.buildFailure("未能找到路径");
        }
    }

    /**
     * 深度遍历查找所有路径
     *
     * @param start 起始顶点
     * @param end   终止顶点
     * @return 返回ArrayList
     */
    private ArrayList<PathVO> findPathDFS(Vertex start, Vertex end) {
        visited[start.getId()] = true;
        ArrayList<PathVO> paths = new ArrayList<>();

        // --- 遍历邻接矩阵 ---
        for (int i = 0; i < adjacencyMatrix.getVerticesNum(); i++) {
            if (adjacencyMatrix.getMatrix(start.getId(), i) && !visited[i]) {
                stack.add(getEdgeByVertex(start, vertexes.get(i)));
                if (i == end.getId()) {
                    PathVO p = new PathVO();
                    for (Edge e : stack) {
                        p.addEdge(e);
                    }
                    paths.add(p);
                } else {
                    paths.addAll(findPathDFS(vertexes.get(i), end));
                }
                pop(stack);
            }
        }

        visited[start.getId()] = false;
        return paths;
    }

    /**
     * 通过顶点id找到对应的边
     *
     * @param start
     * @param end
     * @return
     */
    private Edge getEdgeByVertex(Vertex start, Vertex end) {
        for (Edge e : edges) {
            if (e.getStart().getId() == start.getId() && e.getEnd().getId() == end.getId()) {
                return e;
            }
        }
        return null;
    }

    /**
     * 边出栈
     *
     * @param paths
     * @return
     */
    private ArrayList<Edge> pop(ArrayList<Edge> paths) {
        if (paths.size() == 0) {
            return paths;
        }
        paths.remove(paths.size() - 1);
        return paths;
    }

}

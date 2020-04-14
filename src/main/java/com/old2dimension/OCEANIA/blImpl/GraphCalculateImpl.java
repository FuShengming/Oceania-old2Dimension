package com.old2dimension.OCEANIA.blImpl;


import com.old2dimension.OCEANIA.bl.GraphCalculateBL;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.*;
import gr.gousiosg.javacg.stat.JCallGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class GraphCalculateImpl implements GraphCalculateBL {

    public AdjacencyMatrix adMatrix;
    public ArrayList<Edge> allEdges;
    public DomainSet domainSet;

    public ArrayList<Vertex> getAllVertexes() {
        return allVertexes;
    }

    public ArrayList<Vertex> allVertexes;
    private boolean[][] visited;
    int curUserId;
    int curCodeId;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CodeRepository codeRepository;

    public void setCodeRepository(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseVO getGraph(UserAndCodeForm userAndCodeForm) {

        User currentUser = userRepository.findUserById(userAndCodeForm.getUserId());
        if (currentUser == null) {
            return ResponseVO.buildFailure("no such user");
        }
        Code curCode = codeRepository.findCodeByIdAndUserId(userAndCodeForm.getCodeId(), userAndCodeForm.getUserId());
        if (curCode == null) {
            return ResponseVO.buildFailure("no such code");
        }
        if (curCode.getIs_default() == 1) {
            curUserId = userAndCodeForm.getUserId();
            curCodeId = userAndCodeForm.getCodeId();
            System.out.println("iTrust");
            initializeGraph("call_dependencies_update.txt");
            WeightForm weightForm = new WeightForm();
            weightForm.setWeightName("closeness");
            weightForm.setWeightValue(0.15);
            ArrayList<WeightForm> weightForms = new ArrayList<WeightForm>();
            weightForms.add(weightForm);
            getConnectedDomains(weightForms);
            DependencyGraphVO dependencyGraphVO = new DependencyGraphVO(new DomainSetVO(domainSet));
            return ResponseVO.buildSuccess(dependencyGraphVO);
        } else {
            curUserId = userAndCodeForm.getUserId();
            curCodeId = userAndCodeForm.getCodeId();
            System.out.println("not iTrust");
            initializeGraph("src/main/resources/dependencies/" + curCode.getId() + ".txt");
            WeightForm weightForm = new WeightForm();
            weightForm.setWeightName("closeness");
            weightForm.setWeightValue(0);
            ArrayList<WeightForm> weightForms = new ArrayList<WeightForm>();
            weightForms.add(weightForm);
            getConnectedDomains(weightForms);
            if (curCode.getNumOfVertices() == 0 || curCode.getNumOfEdges() == 0 || curCode.getNumOfDomains() == 0) {
                curCode.setNumOfVertices(allVertexes.size());
                curCode.setNumOfEdges(allEdges.size());
                curCode.setNumOfDomains(domainSet.getDomainSetSize());
                codeRepository.save(curCode);
            }
            weightForm.setWeightValue(0.15);
            getConnectedDomains(weightForms);
            DependencyGraphVO dependencyGraphVO = new DependencyGraphVO(new DomainSetVO(domainSet));

            return ResponseVO.buildSuccess(dependencyGraphVO);
        }

    }

    public ResponseVO filterByWeightForm(ArrayList<WeightForm> weightForms) {
        try {
            for (WeightForm w : weightForms) {
                if (w.getWeightValue() < 0 || w.getWeightValue() > 1) {
                    return ResponseVO.buildFailure("closeness should be between 0 and 1(including 0 and 1)");
                }
            }
            return ResponseVO.buildSuccess(new DependencyGraphVO(new DomainSetVO(filterByWeights(weightForms))));
        } catch (Exception e) {
            return ResponseVO.buildFailure("Failure");
        }
    }

    public GraphCalculateImpl() {
    }


    public ResponseVO getConnectedDomains(ArrayList<WeightForm> weightForms) {
        try {
            domainSet = filterByWeights(weightForms);
//            domainSet.sortByVerticesNum();

            return ResponseVO.buildSuccess(new DomainSetVO(domainSet));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("连通域生成失败");
        }
    }

    public ResponseVO getAmbiguousFuncInfos(String message) {
        System.out.println(message);
        ArrayList<VertexVO> res = new ArrayList<VertexVO>();
        boolean have1 = false;
        boolean have2 = false;
        boolean noParam = false;
        String[] args = null;
        String param = null;
        String mes = null;

        if (message.contains("(")) {
            have1 = true;
            param = message.substring(message.indexOf("(") + 1, message.length() - 1);
            if (param.length() == 0) {
                noParam = true;
            } else {
                args = param.split(",");
            }
            mes = message.substring(0, message.indexOf("("));
            if (mes.contains(":")) {
                int colon = mes.indexOf(":");
                mes = mes.substring(0, colon) + "." + mes.substring(colon + 1);
            }
        } else {
            if (message.contains(",")) {
                have2 = true;
                args = message.split(",");
            } else {
                if (message.contains(":")) {
                    int colon = message.indexOf(":");
                    message = message.substring(0, colon) + "." + message.substring(colon + 1);
                }
            }
        }

        for (Vertex v : allVertexes) {
            boolean isPossible = false;
            if (!have1) {
                if (have2) {
                    String[] vArgs = v.getArgs();
                    if (vArgs.length >= args.length) {
                        for (int i = 0; i < vArgs.length - args.length + 1; i++) {
                            boolean check = true;
                            for (int j = 0; j < args.length; j++) {
                                if (!(vArgs[i + j].contains(args[j].trim())))
                                    check = false;
                            }
                            if (check)
                                isPossible = true;
                        }
                    }
                } else {
                    String check = v.getBelongPackage() + "." + v.getBelongClass() + "." + v.getFuncName();
                    for (int i = 0; i < v.getArgs().length; i++) {
                        check += " " + v.getArgs()[i];
                    }
                    if (check.contains(message))
                        isPossible = true;
                }
            } else {
                String check = v.getBelongPackage() + "." + v.getBelongClass() + "." + v.getFuncName();
                if (check.contains(mes)) {
                    if (noParam) {
                        isPossible = true;
                    } else {
                        String[] vArgs = v.getArgs();
                        if (vArgs.length >= args.length) {
                            for (int i = 0; i < vArgs.length - args.length + 1; i++) {
                                boolean check2 = true;
                                for (int j = 0; j < args.length; j++) {
                                    if (!(vArgs[i + j].contains(args[j].trim())))
                                        check2 = false;
                                }
                                if (check2)
                                    isPossible = true;
                            }
                        }
                    }
                }
            }
            if (isPossible) {
                VertexVO vertexVO = new VertexVO();
                vertexVO.setBelongPackage(v.getBelongPackage());
                vertexVO.setBelongClass(v.getBelongClass());
                vertexVO.setId(v.getId());
                vertexVO.setFuncName(v.getFuncName());
                vertexVO.setArgs(v.getArgs());

                res.add(vertexVO);
            }
        }

        return ResponseVO.buildSuccess(res);
    }

    public void initializeGraph(String filename) {

        ArrayList<String> lines = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                String curData = str.substring(2);
                if (!lines.contains(curData))
                    lines.add(curData);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("文件读取失败");
        }

        // 对ArrayList中存储的字符串进行处理


        ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
        Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        int indexOfVertex = 0;
        int indexOfEdge = 0;

        for (String curLine : lines) {
            boolean isInvalid = false;
            String v1String = curLine.substring(0, curLine.indexOf(" "));
            String v2String = curLine.substring(curLine.indexOf(" ") + 4);
            if (!vertexMap.containsKey(v1String)) {
                Vertex curVertex = str2Vertex(v1String);
                if (curVertex != null) {
                    curVertex.setId(indexOfVertex);
                    indexOfVertex++;
                    vertexMap.put(v1String, curVertex);
                    vertexList.add(curVertex);
                } else {
                    isInvalid = true;
                }
            }

            if (!vertexMap.containsKey(v2String)) {

                Vertex curVertex = str2Vertex(v2String);
                if (curVertex != null) {
                    curVertex.setId(indexOfVertex);
                    indexOfVertex++;
                    vertexMap.put(v2String, curVertex);
                    vertexList.add(curVertex);
                } else {
                    isInvalid = true;
                }
            }
            if (!isInvalid) {
                Edge curEdge = new Edge();
                curEdge.setStart(vertexMap.get(v1String));
                curEdge.setEnd(vertexMap.get(v2String));
                curEdge.setId(indexOfEdge);
                edgeList.add(curEdge);
                indexOfEdge++;
            }
        }

        adMatrix = new AdjacencyMatrix(vertexList.size());
        allEdges = edgeList;
        allVertexes = vertexList;
        //---初始化邻接矩阵---

        for (Edge curEdge : edgeList) {
            adMatrix.setMatrix(curEdge.getStart().getId(), curEdge.getEnd().getId(), true);
        }

        for (Vertex curVertex : vertexList) {
            int id = curVertex.getId();
            int inDegree = 0;
            int outDegree = 0;
            for (int j = 0; j < vertexList.size(); j++) {
                if (adMatrix.getMatrix(id, j)) {
                    outDegree++;
                }
                if (adMatrix.getMatrix(j, id)) {
                    inDegree++;
                }
            }
            curVertex.setOutDegree(outDegree);
            curVertex.setInDegree(inDegree);
        }

        //---初始化紧密度---
        for (Edge e : edgeList) {
            Closeness curCloseness = new Closeness();
            curCloseness.setWeightValue(calculateCloseness(e));
            e.addWeight(curCloseness);
        }

        //System.out.println(vertexList.size());

    }

    private Vertex str2Vertex(String curString) {
        Vertex curVertex = new Vertex();
        String withoutArg = curString.substring(0, curString.indexOf("("));
        String packageName = withoutArg.substring(0, withoutArg.lastIndexOf("."));
        String className = withoutArg.substring(withoutArg.lastIndexOf(".") + 1, withoutArg.indexOf(":"));
        String funcName = withoutArg.substring(withoutArg.indexOf(":") + 1);
        if (funcName.contains("$")) {
            return null;
        }

        String[] args = curString.substring(curString.indexOf("(") + 1, curString.indexOf(")")).split(",");
        curVertex.setBelongPackage(packageName);
        curVertex.setBelongClass(className);
        curVertex.setFuncName(funcName);
        curVertex.setArgs(args);
        return curVertex;
    }

    private double calculateCloseness(Edge e) {
        Vertex start = e.getStart();
        Vertex end = e.getEnd();
        return 2.0 / (end.getInDegree() + start.getOutDegree());

    }

    /*
     * 根据阈值筛选
     * input: 连通域集合、阈值集合
     * output: 连通域集合
     */
    public DomainSet filterByWeights(ArrayList<WeightForm> thresholds) {
        DomainSet filteredDomainSet = new DomainSet();
        filteredDomainSet.setThresholds(thresholds);
        ArrayList<Domain> domains = new ArrayList<>();
        int index = 0;
        visited = new boolean[allVertexes.size()][allVertexes.size()];
        for (int i = 0; i < allVertexes.size(); i++) {
            for (int j = 0; j < allVertexes.size(); j++) {
                visited[i][j] = false;
            }
        }

        for (int i = 0; i < allVertexes.size(); i++) {
            for (int j = 0; j < allVertexes.size(); j++) {
                if (visited[i][j]) continue;
                Domain domain = new Domain(new ArrayList<>(), new ArrayList<>());
                generateDomain(domain, i, j, thresholds);
                if (domain.getEdges().size() == 0) continue;
                domain.setId(index);
                index++;

                domains.add(domain);
            }
        }
        filteredDomainSet.setDomains(domains);
        return filteredDomainSet;
    }

    private Edge findEdge(ArrayList<Edge> edges, int startId, int EndId) {
        for (Edge edge : edges) {
            if (edge.getStart().getId() == startId && edge.getEnd().getId() == EndId)
                return edge;
        }
        return null;
    }

    private void generateDomain(Domain domain, int startId, int endId, ArrayList<WeightForm> thresholds) {
        if (visited[startId][endId]) return;
        visited[startId][endId] = true;
        if (!adMatrix.getMatrix(startId, endId)) return;
        Edge edge = findEdge(allEdges, startId, endId);
        assert edge != null;
        if (!edge.passFilter(thresholds)) return;
        domain.addEdges(edge);
        if (!domain.contains(edge.getStart())) domain.addVertex(edge.getStart());
        if (!domain.contains(edge.getEnd())) domain.addVertex(edge.getEnd());
        for (int i = 0; i < allVertexes.size(); i++) {
            generateDomain(domain, endId, i, thresholds);
            generateDomain(domain, startId, i, thresholds);
            generateDomain(domain, i, startId, thresholds);
            generateDomain(domain, i, endId, thresholds);
        }
    }
}

package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.GraphCalculate;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.DomainSetVO;
import com.old2dimension.OCEANIA.vo.FuncInfoForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.WeightForm;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
public class GraphCalculateImpl implements GraphCalculate {

    public AdjacencyMatrix adMatrix;
    public ArrayList<Edge> allEdges;
    public DomainSet domainSet;
    public ArrayList<Vertex> allVertexes;

    public GraphCalculateImpl() {
    }

    public ResponseVO findPath(FuncInfoForm func1, FuncInfoForm func2) {
        return new ResponseVO();
    }

    public ResponseVO getConnectedDomains(ArrayList<WeightForm> weightForms) {
        try {
            domainSet = filterByWeights(allEdges, weightForms);
            domainSet.sortByVerticesNum();
            return ResponseVO.buildSuccess(new DomainSetVO(domainSet));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("连通域生成失败");
        }
    }

    public HashSet<FuncInfoForm> getAmbiguousFuncInfos(String funcName) {
        String pkgAndClassAndFunc = funcName;
        String pkgAndClass = null;
        String pkg = null;
        String className = null;
        String fName = null;
        String param = null;
        if (funcName.charAt(funcName.length() - 1) == ')') {
            param = funcName.substring(funcName.indexOf("(") + 1, funcName.length() - 1).trim();
            pkgAndClassAndFunc = pkgAndClassAndFunc.substring(0, funcName.indexOf("("));
        }
        //输入时类和方法间是.还是:
        if (pkgAndClassAndFunc.indexOf(":") != -1) {
            fName = pkgAndClassAndFunc.substring(pkgAndClassAndFunc.indexOf(":") + 1);
            pkgAndClass = pkgAndClassAndFunc.substring(0, pkgAndClassAndFunc.indexOf(":"));
            if (pkgAndClass.lastIndexOf(".") != -1) {
                className = pkgAndClass.substring(pkgAndClass.lastIndexOf(".") + 1);
                pkg = pkgAndClass.substring(0, pkgAndClass.lastIndexOf("."));
            } else {
                className = pkgAndClass;
                pkg = null;
            }
        } else if (pkgAndClassAndFunc.lastIndexOf(".") != -1){
            fName = pkgAndClassAndFunc.substring(pkgAndClassAndFunc.indexOf(".") + 1);
            pkgAndClass = pkgAndClassAndFunc.substring(0, pkgAndClassAndFunc.indexOf("."));
            if (pkgAndClass.lastIndexOf(".") != -1) {
                className = pkgAndClass.substring(pkgAndClass.lastIndexOf(".") + 1);
                pkg = pkgAndClass.substring(0, pkgAndClass.lastIndexOf("."));
            } else {
                className = pkgAndClass;
                pkg = null;
            }
        } else {
            fName = pkgAndClassAndFunc;
            className = null;
            pkg = null;
        }

        HashSet<FuncInfoForm> res = new HashSet<FuncInfoForm>();
        for (Vertex v : allVertexes) {
            String vPkg = v.getBelongPackage();
            String vClass = v.getBelongClass();
            String vName = v.getFuncName();
            String[] vArgs = v.getArgs();

            boolean isSame = true;
            if (!(vName.equals(fName)))
                isSame = false;
            if (!((className == null) || (className.equals(vClass))))
                isSame = false;
            if (pkg != null) {
                if (pkg.length() > vPkg.length()) {
                    isSame = false;
                } else {
                    String part = vPkg.substring(vPkg.length() - pkg.length());
                    if (!(pkg.equals(part)))
                        isSame = false;
                }
            }
            //处理参数方面
            if (param != null) {
                String[] args = param.split(",");
                if (args.length != vArgs.length) {
                    isSame = false;
                } else {
                    for (int i = 0; i < args.length; i++) {
                        if (args[i].trim().length() > vArgs[i].length()) {
                            isSame = false;
                        } else {
                            String part = vArgs[i].substring(vArgs[i].length() - args[i].trim().length());
                            if (!(args[i].trim().equals(part)))
                                isSame = false;
                        }
                    }
                }
            }

            if (isSame) {
                FuncInfoForm funcInfoForm = new FuncInfoForm();
                funcInfoForm.setBelongPackage(v.getBelongPackage());
                funcInfoForm.setBelongClass(v.getBelongClass());
                funcInfoForm.setFuncName(v.getFuncName());
                funcInfoForm.setId(v.getId());
                funcInfoForm.setArgs(v.getArgs());

                res.add(funcInfoForm);
            }
        }
        return res;

    }

    public void initializeGraph(String filename ){
        ArrayList<String> lines = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                // System.out.println(str.substring(2));
                String curData=str.substring(2);
                if(!lines.contains(curData))
                    lines.add(curData);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理


        ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
        Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        int indexOfVertex = 0;
        int indexOfEdge = 0;
        for (String curLine : lines) {

            String v1String = curLine.substring(0, curLine.indexOf(" "));
            String v2String = curLine.substring(curLine.indexOf(" ") + 4);
            if (!vertexMap.containsKey(v1String)) {
                Vertex curVertex = str2Vertex(v1String);
                curVertex.setId(indexOfVertex);
                indexOfVertex++;
                vertexMap.put(v1String, curVertex);
                vertexList.add(curVertex);
            }

            if (!vertexMap.containsKey(v2String)) {
                Vertex curVertex = str2Vertex(v2String);
                curVertex.setId(indexOfVertex);
                indexOfVertex++;
                vertexMap.put(v2String, curVertex);
                vertexList.add(curVertex);
            }

            Edge curEdge = new Edge();
            curEdge.setStart(vertexMap.get(v1String));
            curEdge.setEnd(vertexMap.get(v2String));
            curEdge.setId(indexOfEdge);
            edgeList.add(curEdge);
            indexOfEdge++;
        }
        adMatrix=new AdjacencyMatrix(vertexList.size());
        allEdges=edgeList;
        allVertexes=vertexList;
        //---初始化邻接矩阵---
        for (Edge curEdge : edgeList) {
            adMatrix.setMatrix(curEdge.getStart().getId(), curEdge.getEnd().getId(), true);
        }

        //--初始化出度入度--
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
    public DomainSet filterByWeights(ArrayList<Edge> edges, ArrayList<WeightForm> thresholds) {
        DomainSet filteredDomainSet = new DomainSet();
        filteredDomainSet.setThresholds(thresholds);
        ArrayList<Domain> domains = new ArrayList<>();
        int index = 0;
        for (Edge edge : edges) {
            if (!edge.passFilter(thresholds)) continue;
            boolean connected = false;
            for (Domain domain : domains) {
                if (domain.contains(edge.getStart()) || domain.contains(edge.getEnd())) {
                    domain.addEdges(edge);
                    if (!domain.contains(edge.getStart())) domain.addVertex(edge.getStart());
                    if (!domain.contains(edge.getEnd())) domain.addVertex(edge.getEnd());
                    connected = true;
                    break;
                }
            }
            if (!connected) {
                Domain domain = new Domain(new ArrayList<>(), new ArrayList<>());
                domain.addEdges(edge);
                domain.addVertex(edge.getStart());
                domain.addVertex(edge.getEnd());
                domain.setId(index);
                index++;
                domains.add(domain);
            }
        }
        filteredDomainSet.setDomains(domains);
        return filteredDomainSet;
    }
}

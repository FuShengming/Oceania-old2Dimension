//package com.old2dimension.OCEANIA;
//
//import com.old2dimension.OCEANIA.bl.PathBL;
//import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
//import com.old2dimension.OCEANIA.blImpl.PathBLImpl;
//import com.old2dimension.OCEANIA.po.Edge;
//import com.old2dimension.OCEANIA.po.Vertex;
//import com.old2dimension.OCEANIA.vo.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Scanner;
//
//@Component
//public class OceaniaRunner implements ApplicationRunner {
//    @Autowired
//    GraphCalculateImpl graphCalculate;
//    @Autowired
//    PathBLImpl pathBL;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception{
////
////        -----以下为初始化方法-----
//        initializeGraph(graphCalculate,"call_dependencies_update.txt");
//        pathBL=new PathBLImpl(graphCalculate);
//
////        --------以下为输入输出函数-----
////        ------打印顶点、边和连通域数目-----
//        printGraphInfo(graphCalculate);
//
////        ------紧密度阈值过滤------
//        closenessFilter(graphCalculate);
//
////        ------路径查找------
//       findPath(pathBL);
//
//    }
//
//    public void initializeGraph(GraphCalculateImpl graphCalculate,String filename){
//        //-------初始化最初的紧密度筛选为0----------
//        WeightForm initialWeight=new WeightForm();
//        initialWeight.setWeightName("closeness");
//        initialWeight.setWeightValue(0);
//        ArrayList<WeightForm> initialWeights=new ArrayList<WeightForm>();
//        initialWeights.add(initialWeight);
//
//        //------初始化图数据-------------
//        graphCalculate.initializeGraph(filename);
//        graphCalculate.getConnectedDomains(initialWeights);
//    }
//
//    public void printGraphInfo(GraphCalculateImpl graphCalculate){
//
//            System.out.println("图中顶点数为：" + (graphCalculate.allVertexes.size() + ""));
//            System.out.println("图中边数目为：" + (graphCalculate.allEdges.size() + ""));
//            System.out.println("图中连通域个数为：" + (graphCalculate.domainSet.getDomainSetSize() + ""));
//
//
//    }
//
//    public void closenessFilter(GraphCalculateImpl graphCalculate) throws NumberFormatException{
//        System.out.println();
//        System.out.println("--------连通域过滤--------");
//        try {
//            Scanner sc = new Scanner(System.in);
//
//            double closeness=-1.0;
//            while (closeness<0||closeness>1){
//                System.out.print("请输入用于过滤的紧密度阈值(非负小数或整数)：");
//                String closenessStr = sc.nextLine();
//                closeness = Double.parseDouble(closenessStr);
//                if(closeness<0)System.out.println("紧密度阈值必须大于等于0！");
//                if(closeness>1)System.out.println("紧密度阈值必须小于等于1！");
//            }
//            WeightForm weightForm=new WeightForm();
//            weightForm.setWeightName("closeness");
//            weightForm.setWeightValue(closeness);
//            ArrayList<WeightForm> weightForms=new ArrayList<WeightForm>();
//            weightForms.add(weightForm);
//            DomainSetVO domainSet = (DomainSetVO) graphCalculate.getConnectedDomains(weightForms).getContent();
//            ArrayList<DomainVO> domainList= domainSet.getDomainVOs();
//            int domainSetSize=domainList.size();
//            for(int i = 0;i<domainSetSize;i++){
//                DomainVO curDomain=domainList.get(i);
//                int verticesNum=curDomain.getVerticesNum();
//                System.out.println("-------连通域"+(i+1+"")+"-------");
//                System.out.println("连通域顶点数："+(verticesNum+""));
//                ArrayList<VertexVO> vertices = curDomain.getVertices();
//                int index = 1;
//                for(VertexVO curNode : vertices){
//                    System.out.println("Node"+(index + "")+": "+curNode.getClassNameAndFunc());
//                    index++;
//                }
//                System.out.println();
//                index=1;
//                ArrayList<EdgeVO> edges = curDomain.getEdgeVOS();
//                for(EdgeVO curEdge : edges){
//                    System.out.println("Edge"+(index+"")+": "+curEdge.getEdgeString());
//                    index++;
//                }
//            }
//        }
//        catch (NumberFormatException e){
//            System.out.println("数字格式不正确！");
//            closenessFilter(graphCalculate);
//        }
//
//    }
//
//    public void findPath(PathBL pathBL){
//        VertexVO start= null;
//        VertexVO end= null;
//        System.out.println();
//        System.out.println("--------路径查找--------");
//        System.out.println("请输入起点类/函数/参数名：");
//        Scanner sc=new Scanner(System.in);
//        String nodeStr=sc.nextLine();
//        start=getAmbiguousFunc(nodeStr, sc);
//        if(start!=null){
//            System.out.println("请输入终点类/函数/参数名：");
//            nodeStr=sc.nextLine();
//            end=getAmbiguousFunc(nodeStr, sc);
//            if(end != null){
//                ResponseVO resVO =pathBL.findPath(start,end);
//                if(!resVO.isSuccess()){System.out.println("路径查找失败"); return ;}
//
//                System.out.println("SOURCE NODE: "+start.getClassNameAndFunc());
//                System.out.println("TARGET NODE: "+end.getClassNameAndFunc());
//                System.out.println();
//
//                ArrayList<PathVO> paths= (ArrayList<PathVO>) ((FindPathVO)resVO.getContent()).getPathVOS();
//                if(paths.size()==0){System.out.println("没有找到任何路径");return ;}
//                Collections.sort(paths, new Comparator<PathVO>() {
//                    @Override
//                    public int compare(PathVO pathVO, PathVO t1) {
//                        return (pathVO.getEdges().size() - t1.getEdges().size());
//                    }
//                });
//                System.out.println("共有"+paths.size()+"条路径:\n");
//                int minLength=paths.get(0).getEdges().size();
//                int pathsSize=paths.size();
//                for(int j=1 ; j<=pathsSize; j++){
//                    PathVO curPath=paths.get(j-1);
//                    System.out.println("PATH "+j+":"+getPathString(curPath));
//                }
//
//
//            }
//        }
//
//    }
//
//    private String getPathString(PathVO curPath){
//        String res="";
//        ArrayList<Edge> curEdges=curPath.getEdges();
//        int edgesSize=curEdges.size();
//        for(int k=0; k<edgesSize; k++){
//            Edge curEdge=curEdges.get(k);
//            if(k==0){
//               res+=(curEdge.getStart().getClassNameAndFunc()+" -- "+
//                        curEdge.getWeight("closeness").getWeightValue()+" -->\n"+curEdge.getEnd().getClassNameAndFunc());
//            }
//            else{
//                res+= "-- "+curEdge.getWeight("closeness").getWeightValue()+" -->\n"+curEdge.getEnd().getClassNameAndFunc();
//            }
//        }
//        res+="\n";
//        return res;
//    }
//
//    private VertexVO getAmbiguousFunc(String nodeStr,Scanner sc){
//
//        ResponseVO resVO =graphCalculate.getAmbiguousFuncInfos(nodeStr);
//        if(!resVO.isSuccess()){System.out.println("获取函数信息失败"); return null;}
//        ArrayList<VertexVO> vertices= (ArrayList<VertexVO>)resVO.getContent();
//
//        if(vertices.size()==0){System.out.println("没有找到与此类/函数名匹配的函数顶点"); return null;}
//        else{
//            int size=vertices.size();
//            for(int i=0; i<size; i++){
//                System.out.println(i+1+". "+vertices.get(i).getFullName());
//            }
//            System.out.println();
//            System.out.println("请输入你所选择的节点序号（不超过序号范围的正整数）：");
//            String indexStr=sc.nextLine();
//            int index = 0;
//            try{
//                index=Integer.parseInt(indexStr);
//            }
//            catch (NumberFormatException e){
//                System.out.println("序号格式不正确，请输入不超过序号范围的正整数");
//                return null;
//            }
//            if(index == 0){System.out.println("序号格式不正确，请输入不超过序号范围的正整数"); return null;}
//            return vertices.get(index-1);
//        }
//
//    }
//
//}

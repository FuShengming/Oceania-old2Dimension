package com.old2dimension.OCEANIA;

import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.po.Vertex;
import com.old2dimension.OCEANIA.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

@Component
public class OceaniaRunner implements ApplicationRunner {
    @Autowired
    GraphCalculateImpl graphCalculate;
    @Override
    public void run(ApplicationArguments args) throws Exception{
        //-------初始化最初的紧密度筛选为0----------
        WeightForm initialWeight=new WeightForm();
        initialWeight.setWeightName("closeness");
        initialWeight.setWeightValue(0);
        ArrayList<WeightForm> initialWeights=new ArrayList<WeightForm>();
        initialWeights.add(initialWeight);

        //------初始化图数据-------------
        graphCalculate.initializeGraph("call_dependencies_update.txt");
        graphCalculate.getConnectedDomains(initialWeights);

        //------打印顶点、边和连通域数目-----
        printGraphInfo(graphCalculate);
        //------紧密度阈值过滤------
        closenessFilter(graphCalculate);

    }

    private void printGraphInfo(GraphCalculateImpl graphCalculate) throws Exception{
        try {
            System.out.println("图中顶点数为：" + (graphCalculate.allVertexes.size() + ""));
            System.out.println("图中边数目为：" + (graphCalculate.allEdges.size() + ""));
            System.out.println("图中连通域个数为：" + (graphCalculate.domainSet.getDomainSetSize() + ""));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void closenessFilter(GraphCalculateImpl graphCalculate) throws NumberFormatException{
        try {
            Scanner sc = new Scanner(System.in);

            double closeness=-1.0;
            while (closeness<0){
                System.out.print("请输入用于过滤的紧密度阈值(非负小数或整数)：");
                String closenessStr = sc.nextLine();
                closeness = Double.parseDouble(closenessStr);
                System.out.println("紧密度阈值必须大于零！");
            }

            WeightForm weightForm=new WeightForm();
            weightForm.setWeightName("closeness");
            weightForm.setWeightValue(closeness);
            ArrayList<WeightForm> weightForms=new ArrayList<WeightForm>();
            weightForms.add(weightForm);
            DomainSetVO domainSet = (DomainSetVO) graphCalculate.getConnectedDomains(weightForms).getContent();
            ArrayList<DomainVO> domainList= domainSet.getDomainVOs();
            int domainSetSize=domainList.size();
            for(int i = 0;i<domainSetSize;i++){
                DomainVO curDomain=domainList.get(i);
                int verticesNum=curDomain.getVerticesNum();
                System.out.println("-------连通域"+(i+1+"")+"-------");
                System.out.println("连通域顶点数："+(verticesNum+""));
                ArrayList<FuncInfoForm> vertices = curDomain.getVertices();
                int index = 1;
                for(FuncInfoForm curNode : vertices){
                    System.out.println("Node"+(index + "")+": "+curNode.getClassNameAndFunc());
                    index++;
                }
                System.out.println();
                index=1;
                ArrayList<EdgeVO> edges = curDomain.getEdgeVOS();
                for(EdgeVO curEdge : edges){
                    System.out.println("Edge"+(index+"")+": "+curEdge.getEdgeString());
                    index++;
                }

            }


        }
        catch (NumberFormatException e){
            System.out.println("数字格式不正确！");
            closenessFilter(graphCalculate);
        }

    }

}

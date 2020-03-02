package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.AdjacencyMatrix;
import com.old2dimension.OCEANIA.po.Domain;
import com.old2dimension.OCEANIA.po.Edge;
import com.old2dimension.OCEANIA.vo.FuncInfoForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.WeightForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class GraphCalculateImp implements GraphCaculate {

    AdjacencyMatrix adMatrix ;
    Domain initialGraph;

    public GraphCalculateImp(){
        initializeGraph();
    }
    public ResponseVO findPath(FuncInfoForm func1, FuncInfoForm func2){return new ResponseVO();}
    public ResponseVO getConnectedDomains(ArrayList<WeightForm> weightForms){return new ResponseVO();}
    public ResponseVO getAmbiguousFuncInfos(String funcName){return new ResponseVO();}

    public void initializeGraph(){
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileReader fr = new FileReader("call_dependencies.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
               // System.out.println(str.substring(2));
                lines.add(str.substring(2));
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理

        Set<String> vertexSet=new HashSet<String>();
        Set<Edge> edgeSet=new HashSet<Edge>();
        for(int i=0;i<lines.size();i++){
            String curLine=lines.get(i);
            String v1String=curLine.substring(0,curLine.indexOf(" "));
            String v2String=curLine.substring(curLine.indexOf(" ")+4);
            vertexSet.add(v1String);
            vertexSet.add(v2String);
        }
        adMatrix=new AdjacencyMatrix(vertexSet.size());
        //-----------------------

    }
}

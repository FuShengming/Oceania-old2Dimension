package com.old2dimension.OCEANIA.vo;
import java.util.ArrayList;

public class CodeNodeVO {
    private String text;

    private ArrayList<CodeNodeVO> nodes;

    public CodeNodeVO(){
        this.text="";
        this.nodes=new ArrayList<CodeNodeVO>();
    }

    public CodeNodeVO(String text){
        this.text=text;
        this.nodes=new ArrayList<CodeNodeVO>();
    }

    public CodeNodeVO(String text, ArrayList<CodeNodeVO> nodes) {
        this.text = text;
        this.nodes = nodes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<CodeNodeVO> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<CodeNodeVO> nodes) {
        this.nodes = nodes;
    }

    public void addChild(CodeNodeVO child){
        this.nodes.add(child);
    }
}
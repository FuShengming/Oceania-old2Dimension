package com.old2dimension.OCEANIA.po;
import javax.persistence.Cacheable;
import java.util.ArrayList;

public class CodeNode {
    private String text;

    private ArrayList<CodeNode> nodes;

    public CodeNode(){
        this.text="";
        this.nodes=new ArrayList<CodeNode>();
    }

    public CodeNode(String text){
        this.text=text;
        this.nodes=new ArrayList<CodeNode>();
    }

    public CodeNode(String text, ArrayList<CodeNode> nodes) {
        this.text = text;
        this.nodes = nodes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<CodeNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<CodeNode> nodes) {
        this.nodes = nodes;
    }

    public void addChild(CodeNode child){
        this.nodes.add(child);
    }
}

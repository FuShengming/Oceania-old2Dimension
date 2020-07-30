package com.old2dimension.OCEANIA.vo;

public class CodeMesVO {
    private String codeName;
    private int numOfVertex;
    private int numOfEdge;
    private int numOfDomain;
    private int numOfVertexLabel;
    private int numOfEdgeLabel;
    private int numOfDomainLabel;

    public CodeMesVO() {
    }

    public CodeMesVO(String codeName, int numOfVertex, int numOfEdge, int numOfDomain, int numOfVertexLabel, int numOfEdgeLabel, int numOfDomainLabel) {
        this.codeName = codeName;
        this.numOfVertex = numOfVertex;
        this.numOfEdge = numOfEdge;
        this.numOfDomain = numOfDomain;
        this.numOfVertexLabel = numOfVertexLabel;
        this.numOfEdgeLabel = numOfEdgeLabel;
        this.numOfDomainLabel = numOfDomainLabel;
    }

    public String getCodeName() {
        return codeName;
    }

//    public void setCodeName(String codeName) {
//        this.codeName = codeName;
//    }

    public int getNumOfVertex() {
        return numOfVertex;
    }

//    public void setNumOfVertex(int numOfVertex) {
//        this.numOfVertex = numOfVertex;
//    }

    public int getNumOfEdge() {
        return numOfEdge;
    }

//    public void setNumOfEdge(int numOfEdge) {
//        this.numOfEdge = numOfEdge;
//    }

    public int getNumOfDomain() {
        return numOfDomain;
    }

//    public void setNumOfDomain(int numOfDomain) {
//        this.numOfDomain = numOfDomain;
//    }

    public int getNumOfVertexLabel() {
        return numOfVertexLabel;
    }

//    public void setNumOfVertexLabel(int numOfVertexLabel) {
//        this.numOfVertexLabel = numOfVertexLabel;
//    }

    public int getNumOfEdgeLabel() {
        return numOfEdgeLabel;
    }

//    public void setNumOfEdgeLabel(int numOfEdgeLabel) {
//        this.numOfEdgeLabel = numOfEdgeLabel;
//    }

    public int getNumOfDomainLabel() {
        return numOfDomainLabel;
    }

//    public void setNumOfDomainLabel(int numOfDomainLabel) {
//        this.numOfDomainLabel = numOfDomainLabel;
//    }
}

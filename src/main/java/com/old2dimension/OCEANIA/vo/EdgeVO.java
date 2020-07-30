package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Edge;
import com.old2dimension.OCEANIA.po.Weight;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EdgeVO {
    private VertexVO start;
    private VertexVO end;

    private ArrayList<WeightForm> weights;

    private int id;

    public EdgeVO() {
    }

    public EdgeVO(Edge edge) {
        this.id = edge.getId();
        this.start = new VertexVO(edge.getStart());
        this.end = new VertexVO(edge.getEnd());
        this.weights = new ArrayList<>();
        for (Weight w : edge.getWeights()) {
            this.weights.add(new WeightForm(w));
        }
    }

    public VertexVO getStart() {
        return start;
    }

    public void setStart(VertexVO start) {
        this.start = start;
    }

    public VertexVO getEnd() {
        return end;
    }

    public void setEnd(VertexVO end) {
        this.end = end;
    }

    public ArrayList<WeightForm> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<WeightForm> weights) {
        this.weights = weights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEdgeString() {
        BigDecimal bd = new BigDecimal(weights.get(0).getWeightValue() + "");

        return start.getClassNameAndFunc() + " -- " + (bd.setScale(4, BigDecimal.ROUND_DOWN)) + " --> " + end.getClassNameAndFunc();
    }
}

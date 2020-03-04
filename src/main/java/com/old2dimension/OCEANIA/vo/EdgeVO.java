package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Edge;
import com.old2dimension.OCEANIA.po.Weight;

import java.util.ArrayList;

public class EdgeVO {
    private FuncInfoForm start;
    private FuncInfoForm end;

    private ArrayList<WeightForm> weights;

    private int id;

    public EdgeVO() {
    }

    public EdgeVO(Edge edge) {
        this.start=new FuncInfoForm(edge.getStart());
        this.end=new FuncInfoForm(edge.getEnd());
        this.weights = new ArrayList<>();
        for(Weight w:edge.getWeights()){
            this.weights.add(new WeightForm(w));
        }
    }

    public FuncInfoForm getStart() {
        return start;
    }

    public void setStart(FuncInfoForm start) {
        this.start = start;
    }

    public FuncInfoForm getEnd() {
        return end;
    }

    public void setEnd(FuncInfoForm end) {
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
}

package com.old2dimension.OCEANIA.po;

import java.util.ArrayList;

public class Edge {
    private Vertex start;
    private Vertex end;
    private ArrayList<Weight> weights;
    private int id;


    public Edge() {
        weights = new ArrayList<>();
    }


    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void addWeight(Weight weight) {
        weights.add(weight);
    }

    public Weight getWeight(String name) {
        for(Weight w:weights){

            if(w.getWeightName().equals(name)){
                return w;
            }
        }
        System.out.println("没有名字为"+name+"的阈值");
        return null;
    }

}

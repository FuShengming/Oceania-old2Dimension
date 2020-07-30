package com.old2dimension.OCEANIA.po;

import com.old2dimension.OCEANIA.vo.WeightForm;

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
        for (Weight w : weights) {

            if (w.getWeightName().equals(name)) {
                return w;
            }
        }
        System.out.println("没有名字为" + name + "的阈值");
        return null;
    }

    public ArrayList<Weight> getWeights() {
        return weights;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge)) return false;
        return ((Edge) obj).getId() == this.getId();
    }

    public boolean passFilter(ArrayList<WeightForm> thresholds) {
        for (WeightForm threshold : thresholds) {
            for (Weight weight : this.weights) {
                if (!weight.getWeightName().equals(threshold.getWeightName())) continue;
                if (weight.getWeightValue() < threshold.getWeightValue()) return false;
            }
        }
        return true;
    }
}

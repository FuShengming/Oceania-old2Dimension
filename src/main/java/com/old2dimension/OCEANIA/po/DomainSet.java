package com.old2dimension.OCEANIA.po;

import java.util.ArrayList;

public class DomainSet {
    private ArrayList<Weight> thresholds;
    private ArrayList<Domain> domains;

    public int getDomainSetSize() {
       return domains.size();
    }
    public ArrayList<Weight> getThresholds() {
        return thresholds;
    }

    public void addWeight(Weight weight){
        for(Weight w:thresholds){
            if(w.getWeightName().equals(weight.getWeightName())){w.setWeightValue(weight.getWeightValue());
                return;
            }
        }
        thresholds.add(weight);
    }

    public void setThresholds(ArrayList<Weight> thresholds) {
        this.thresholds = thresholds;
    }

    public ArrayList<Domain> getDomains() {
        return domains;
    }
    public void setDomains(ArrayList<Domain> domains) {
        this.domains = domains;
    }
}

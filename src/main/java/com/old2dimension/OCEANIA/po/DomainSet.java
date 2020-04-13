package com.old2dimension.OCEANIA.po;

import com.old2dimension.OCEANIA.vo.WeightForm;

import java.util.ArrayList;

public class DomainSet {
    private ArrayList<WeightForm> thresholds;
    private ArrayList<Domain> domains;

    public int getDomainSetSize() {
        return domains.size();
    }

    public ArrayList<WeightForm> getThresholds() {
        return thresholds;
    }

    public void addThreshold(WeightForm weight) {
        for (WeightForm w : thresholds) {
            if (w.getWeightName().equals(weight.getWeightName())) {
                w.setWeightValue(weight.getWeightValue());
                return;
            }
        }
        thresholds.add(weight);
    }

    public void addThreshold(Weight weight) {
        for (WeightForm w : thresholds) {
            if (w.getWeightName().equals(weight.getWeightName())) {
                w.setWeightValue(weight.getWeightValue());
                return;
            }
        }
        thresholds.add(new WeightForm(weight));
    }

    public void setThresholds(ArrayList<WeightForm> thresholds) {
        this.thresholds = thresholds;
    }

    public ArrayList<Domain> getDomains() {
        return domains;
    }

    public void setDomains(ArrayList<Domain> domains) {
        this.domains = domains;
    }

}

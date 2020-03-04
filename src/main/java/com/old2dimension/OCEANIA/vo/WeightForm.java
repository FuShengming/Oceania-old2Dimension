package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Weight;

public class WeightForm {
    private String weightName;

    private double weightValue;

    public WeightForm() {
    }

    public WeightForm(Weight weight) {
        this.weightName = weight.getWeightName();
        this.weightValue = weight.getWeightValue();
    }

    public WeightForm(String weightName, double weightValue) {
        this.weightName = weightName;
        this.weightValue = weightValue;
    }

    public String getWeightName() {
        return weightName;
    }

    public double getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(double weightValue) {
        this.weightValue = weightValue;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

}

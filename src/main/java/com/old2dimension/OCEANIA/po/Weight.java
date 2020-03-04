package com.old2dimension.OCEANIA.po;

public abstract class Weight {
    protected String weightName;
    protected double weightValue;

    public double getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(double weightValue) {
        this.weightValue = weightValue;
    }

    public String getWeightName() {
        return weightName;
    }

    public Weight() {
    }

    public Weight(String weightName, double weightValue) {
        this.weightName = weightName;
        this.weightValue = weightValue;
    }
}

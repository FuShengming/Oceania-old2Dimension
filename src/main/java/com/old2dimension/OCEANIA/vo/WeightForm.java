package com.old2dimension.OCEANIA.vo;

import com.old2dimension.OCEANIA.po.Weight;

public class WeightForm extends Weight {
    public WeightForm() {
    }

    public WeightForm(String weightName, double weightValue) {
        super(weightName, weightValue);
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

}

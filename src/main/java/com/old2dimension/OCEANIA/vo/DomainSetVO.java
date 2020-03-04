package com.old2dimension.OCEANIA.vo;


import com.old2dimension.OCEANIA.po.Domain;
import com.old2dimension.OCEANIA.po.DomainSet;

import java.util.ArrayList;

public class DomainSetVO {
    private ArrayList<WeightForm> thresholds;

    private ArrayList<DomainVO> domainVOS;

    public DomainSetVO() {
    }

    public DomainSetVO(DomainSet domainSet) {
        this.thresholds = new ArrayList<>();
        this.thresholds.addAll(domainSet.getThresholds());
        this.domainVOS = new ArrayList<>();
        for (Domain domain : domainSet.getDomains()) {
            this.domainVOS.add(new DomainVO(domain));
        }
    }

    public ArrayList<WeightForm> getThresholds() {
        return thresholds;
    }

    public void setThresholds(ArrayList<WeightForm> thresholds) {
        this.thresholds = thresholds;
    }

    public ArrayList<DomainVO> getDomainVOs() {
        return domainVOS;
    }

    public void setDomainVOs(ArrayList<DomainVO> domainVOs) {
        this.domainVOS = domainVOs;
    }

}

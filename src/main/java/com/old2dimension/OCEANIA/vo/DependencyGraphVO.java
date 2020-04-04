package com.old2dimension.OCEANIA.vo;

public class DependencyGraphVO {
  private   DomainSetVO domainSetVO;

    public DependencyGraphVO(DomainSetVO domainSetVO) {
        this.domainSetVO=domainSetVO;
    }

    public DomainSetVO getDomainVOs() {
        return domainSetVO;
    }

    public void setDomainVOs(DomainSetVO domainSetVO) {
        this.domainSetVO = domainSetVO;
    }


}

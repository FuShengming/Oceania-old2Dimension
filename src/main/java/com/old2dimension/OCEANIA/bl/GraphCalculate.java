package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.FuncInfoForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.WeightForm;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
@Component
public interface GraphCalculate {
    public ResponseVO findPath(FuncInfoForm func1,FuncInfoForm func2);
    public ResponseVO getConnectedDomains(ArrayList<WeightForm> weightForms);
    public ResponseVO getAmbiguousFuncInfos(String funcName);
}

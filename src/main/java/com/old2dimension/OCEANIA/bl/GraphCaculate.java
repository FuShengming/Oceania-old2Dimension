package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.FuncInfoForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.WeightForm;

import java.util.ArrayList;

public interface GraphCaculate {
    ResponseVO findPath(FuncInfoForm func1,FuncInfoForm func2);
    ResponseVO getConnectedDomains(ArrayList<WeightForm> weightForms);
    ResponseVO getAmbiguousFuncInfos(String funcName);
}

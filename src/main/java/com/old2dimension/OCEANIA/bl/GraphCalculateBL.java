package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.Closeness;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import com.old2dimension.OCEANIA.vo.WeightForm;

import java.util.*;

public interface GraphCalculateBL {
    //public ResponseVO findPath(FuncInfoForm func1,FuncInfoForm func2);
//    public ResponseVO getConnectedDomains(ArrayList<WeightForm> weightForms);
    public ResponseVO getAmbiguousFuncInfos(String message);

    public ResponseVO getGraph(UserAndCodeForm userAndCodeForm);

    public ResponseVO filterByWeightForm(ArrayList<WeightForm> weightForms);

}

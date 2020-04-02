package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.WorkSpaceBL;
import com.old2dimension.OCEANIA.dao.WorkPlaceRepository;
import com.old2dimension.OCEANIA.po.WorkSpace;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import com.old2dimension.OCEANIA.vo.WorkSpaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkSpaceBLImpl implements WorkSpaceBL {
    @Autowired
    WorkPlaceRepository workPlaceRepository;
    public ResponseVO save(WorkSpaceVO workSpaceVO){
        try{
        workPlaceRepository.save(new WorkSpace(workSpaceVO));
        return ResponseVO.buildSuccess("save a workSpace.");}
        catch (Exception e){

            return ResponseVO.buildFailure("save workSpace Error.");
        }
    }
    public ResponseVO recover(UserAndCodeForm userAndCodeForm){
        try {
            return ResponseVO.buildSuccess(workPlaceRepository.findLatestWorkSpace(userAndCodeForm.getUserId(), userAndCodeForm.getCodeId()));
        }
        catch (Exception e){
            return ResponseVO.buildFailure("recover workSpace Error.");
        }
    }
}

package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.WorkSpaceBL;
import com.old2dimension.OCEANIA.dao.CodeRepository;
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
    @Autowired
    CodeRepository codeRepository;

    public void setCodeRepository(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void setWorkPlaceRepository(WorkPlaceRepository workPlaceRepository) {
        this.workPlaceRepository = workPlaceRepository;
    }

    public ResponseVO save(WorkSpaceVO workSpaceVO) {
        try {
            if (codeRepository.findCodeByIdAndUserId(workSpaceVO.getCodeId(), workSpaceVO.getUserId()) == null) {
                return ResponseVO.buildFailure("do not have that user or code");
            }
            System.out.println("date:" + workSpaceVO.getDate());
            workPlaceRepository.save(new WorkSpace(workSpaceVO));

            return ResponseVO.buildSuccess("save a workSpace.");
        } catch (Exception e) {
            return ResponseVO.buildFailure("save workSpace Error.");
        }
    }

    public ResponseVO recover(UserAndCodeForm userAndCodeForm) {
        try {
            WorkSpace curWS = workPlaceRepository.findLatestWorkSpace(userAndCodeForm.getUserId(), userAndCodeForm.getCodeId());
            if (curWS == null) {
                return ResponseVO.buildFailure("no such workSpace");
            }
            return ResponseVO.buildSuccess(curWS);
        } catch (Exception e) {
            return ResponseVO.buildFailure("recover workSpace Error.");
        }
    }
}

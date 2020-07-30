package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import com.old2dimension.OCEANIA.vo.WorkSpaceVO;

public interface WorkSpaceBL {
    public ResponseVO save(WorkSpaceVO workSpaceVO);

    public ResponseVO recover(UserAndCodeForm userAndCodeForm);
}

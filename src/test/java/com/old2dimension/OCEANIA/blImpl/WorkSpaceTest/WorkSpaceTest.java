package com.old2dimension.OCEANIA.blImpl.WorkSpaceTest;

import com.old2dimension.OCEANIA.blImpl.WorkSpaceBLImpl;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.dao.WorkPlaceRepository;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.po.WorkSpace;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import com.old2dimension.OCEANIA.vo.WorkSpaceVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.util.Date;

import static org.mockito.Mockito.*;

public class WorkSpaceTest {
    @Test
    public void saveTest1() {
        WorkSpaceVO workSpaceVO = new WorkSpaceVO(1, 1, new Date(), 0.1, "");
        WorkSpaceBLImpl workSpaceBL = new WorkSpaceBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(null);
        workSpaceBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = workSpaceBL.save(workSpaceVO);
        Assert.assertEquals(responseVO.getMessage(), "do not have that user or code");

    }

    @Test
    public void saveTest2() {
        WorkSpaceVO workSpaceVO = new WorkSpaceVO(1, 1, new Date(), 0.1, "");
        WorkSpaceBLImpl workSpaceBL = new WorkSpaceBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        WorkPlaceRepository workPlaceRepository = mock(WorkPlaceRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(new Code());
        when(workPlaceRepository.save(any())).thenReturn(new WorkSpace());
        workSpaceBL.setCodeRepository(codeRepository);
        workSpaceBL.setWorkPlaceRepository(workPlaceRepository);
        ResponseVO responseVO = workSpaceBL.save(workSpaceVO);
        Assert.assertEquals(responseVO.getContent(), "save a workSpace.");
    }

    @Test
    public void recoverTest1() {
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
        WorkSpaceBLImpl workSpaceBL = new WorkSpaceBLImpl();
        WorkPlaceRepository workPlaceRepository = mock(WorkPlaceRepository.class);
        when(workPlaceRepository.findLatestWorkSpace(1, 1)).thenReturn(null);
        workSpaceBL.setWorkPlaceRepository(workPlaceRepository);
        ResponseVO responseVO = workSpaceBL.recover(userAndCodeForm);
        Assert.assertEquals(responseVO.getMessage(), "no such workSpace");
    }

    @Test
    public void recoverTest2() {
        WorkSpace expect = new WorkSpace();
        expect.setCloseness(0.5);
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
        WorkSpaceBLImpl workSpaceBL = new WorkSpaceBLImpl();
        WorkPlaceRepository workPlaceRepository = mock(WorkPlaceRepository.class);
        when(workPlaceRepository.findLatestWorkSpace(1, 1)).thenReturn(expect);
        workSpaceBL.setWorkPlaceRepository(workPlaceRepository);
        ResponseVO responseVO = workSpaceBL.recover(userAndCodeForm);
        Assert.assertEquals(((WorkSpace) responseVO.getContent()).getCloseness(), 0.5, 0.00000000000001);
    }
}

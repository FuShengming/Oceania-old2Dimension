package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.WorkSpaceBL;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import com.old2dimension.OCEANIA.vo.WorkSpaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workSpace")
public class WorkSpaceController {
    @Autowired
    WorkSpaceBL workSpaceBL;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVO save(@RequestBody WorkSpaceVO workSpaceVO) {
        return workSpaceBL.save(workSpaceVO);
    }

    @RequestMapping(value = "/recover", method = RequestMethod.POST)
    public ResponseVO recover(@RequestBody UserAndCodeForm userAndCodeForm) {

        return workSpaceBL.recover(userAndCodeForm);
    }
}

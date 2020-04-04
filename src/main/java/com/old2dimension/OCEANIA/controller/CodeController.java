package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.CodeBL;
import com.old2dimension.OCEANIA.vo.VertexVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    CodeBL codeBL;

    @RequestMapping(value = "/getCodeStructure",method = RequestMethod.POST)
    public ResponseVO getCodeStructure(@RequestBody UserAndCodeForm userAndCodeForm){
        return null;
    }

    @RequestMapping(value = "/getFuncCode",method = RequestMethod.POST)
    public ResponseVO getFuncCode(@RequestBody VertexVO vertexVO){
        return null;
    }

    /*
     *上传代码接口
     * 接口未定
     * */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ResponseVO upload(){

        return null;
    }


    /*
     *从现有代码（iTrust）中添加
     *
     * */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseVO add(@RequestBody UserAndCodeForm userAndCodeForm){
        return null;
    }
}

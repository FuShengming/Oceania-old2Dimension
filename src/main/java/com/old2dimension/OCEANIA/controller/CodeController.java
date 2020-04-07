package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.CodeBL;
import com.old2dimension.OCEANIA.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    CodeBL codeBL;

    @RequestMapping(value = "/getCodesByUserId/{userId}",method = RequestMethod.GET)
    public ResponseVO getCodesByUserId(@PathVariable("userId") int userId){
        return  codeBL.getCodesByUserId(userId);
    }

    @RequestMapping(value = "/getCodeStructure",method = RequestMethod.POST)
    public ResponseVO getCodeStructure(@RequestBody UserAndCodeForm userAndCodeForm){
        return codeBL.getCodeStructure(userAndCodeForm);
    }

    @RequestMapping(value = "/getFuncCode",method = RequestMethod.POST)
    public ResponseVO getFuncCode(@RequestBody VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId){
        return codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
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
        return codeBL.addCode(userAndCodeForm);
    }

    @RequestMapping(value = "/modifyName")
    public ResponseVO modifyName(@RequestBody CodeIdAndUserIdAndNameForm codeIdAndUserIdAndNameForm){
        return codeBL.modifyName(codeIdAndUserIdAndNameForm);
    }
}

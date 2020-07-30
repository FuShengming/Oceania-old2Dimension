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

    @RequestMapping(value = "/getCodesByUserId/{userId}", method = RequestMethod.GET)
    public ResponseVO getCodesByUserId(@PathVariable("userId") int userId) {
        return codeBL.getCodesByUserId(userId);
    }

    @RequestMapping(value = "/getCodeStructure", method = RequestMethod.POST)
    public ResponseVO getCodeStructure(@RequestBody UserAndCodeForm userAndCodeForm) {
        return codeBL.getCodeStructure(userAndCodeForm);
    }

    @RequestMapping(value = "/getFuncCode", method = RequestMethod.POST)
    public ResponseVO getFuncCode(@RequestBody VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId) {
        return codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
    }

    @RequestMapping(value = "/delete")
    public ResponseVO delete(@RequestBody UserAndCodeForm userAndCodeForm) {
        System.out.println("delete");
        return codeBL.delete(userAndCodeForm);
    }

    @RequestMapping(value = "/modifyName")
    public ResponseVO modifyName(@RequestBody CodeIdAndUserIdAndNameForm codeIdAndUserIdAndNameForm) {
        return codeBL.modifyName(codeIdAndUserIdAndNameForm);
    }
}

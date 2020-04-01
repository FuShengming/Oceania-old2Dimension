package com.old2dimension.OCEANIA.controller;
import com.old2dimension.OCEANIA.bl.UserBL;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
@RestController

@RequestMapping("/user")
public class UserController {
    @Autowired
    UserBL userBL;

    @RequestMapping("/signUp")
   public ResponseVO signUp(@RequestBody UserVO user){
        return userBL.signUp(user);
    }

    @RequestMapping("/login")
    public ResponseVO login(@RequestBody UserVO user){
        return userBL.login(user.getName(),user.getPwd());
    }

}

package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/getAllUser")
    String getAllUser(){
        ArrayList<User> allUser=(ArrayList<User>) userRepository.findAll();
        System.out.println(allUser.get(0).getName());
        return "index";
    }


}

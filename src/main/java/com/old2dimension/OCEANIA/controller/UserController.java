package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.UserBL;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.utils.JWTUtils;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    final
    UserBL userBL;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserBL userBL, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userBL = userBL;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signUp")
    public ResponseVO signUp(@RequestBody UserVO user, HttpServletResponse response) {
        user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
        ResponseVO r = userBL.signUp(user);
        if (r.isSuccess()) {
            User user1 = (User) r.getContent();
            String token = JWTUtils.createToken(user1.getName(), "ROLE_USER");
            user1.setPwd(null);
            r.setContent(user1);

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return r;
    }

    @RequestMapping("/getById")
    public ResponseVO getById(@RequestParam int[] id) {
        return userBL.getNamesByIds(id);
    }

    @RequestMapping("/getByName")
    public ResponseVO getByName(@RequestParam String name) {
        return userBL.getUserByName(name);
    }
//    @RequestMapping("/getAll")
//    public ResponseVO getAll


}

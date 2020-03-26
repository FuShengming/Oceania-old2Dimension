package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.UserBL;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class UserBLImpl implements UserBL {
    @Autowired
    UserRepository userRepository;
   public ResponseVO getAllUser(){
        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findAll();
        ArrayList<UserVO> users=userList2UserVOList(allUsers);
        return ResponseVO.buildSuccess(users);
    }
    public ResponseVO login(String userName, String pwd){
       User user = userRepository.findUserByName(userName);
       if(user == null){
           return ResponseVO.buildFailure("no such userName : "+userName);}
       if(user.getPwd().equals(pwd)){
           System.out.println("登陆成功");
           return ResponseVO.buildSuccess("login success");}
       else{
           System.out.println("登陆失败");
           return ResponseVO.buildFailure("name or pwd is not correct");}
    }
    public  ResponseVO signUp(UserVO userInfo){
       try {
            User user =new User(userInfo);
            userRepository.save(user);
            return ResponseVO.buildSuccess("sign up success");
       }
       catch (Exception e){
           return ResponseVO.buildFailure("sign up fail");
       }
    }

    private ArrayList<UserVO> userList2UserVOList(ArrayList<User> userList){
        ArrayList<UserVO> res=new ArrayList<UserVO>();
        for(User u : userList){
            res.add(new UserVO(u));
        }
        return res;
    }

}
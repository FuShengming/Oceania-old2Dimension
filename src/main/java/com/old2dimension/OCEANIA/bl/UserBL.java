package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserVO;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserBL {
    ResponseVO getAllUser();
    ResponseVO signUp(UserVO userInfo);
    public User findUserById(int userId);
    ResponseVO getNamesByIds(int[] userIDs);
    ResponseVO getUserByName(String name);
}

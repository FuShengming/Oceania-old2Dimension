package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.StatisticsBL;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class StatisticsBLImpl implements StatisticsBL {
    @Autowired
    UserRepository userRepository;

    public int getNumOfUser() {
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        return users.size();
    }
}

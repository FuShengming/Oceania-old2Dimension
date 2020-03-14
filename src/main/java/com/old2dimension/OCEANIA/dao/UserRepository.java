package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserRepository {
    private static int INDEX_COUNTER = 0;
    private final ArrayList<User> users = new ArrayList<>();

    public UserRepository() {
    }

    public User createUser(final String user_name, final String pwd) {
        final User user = new User(++INDEX_COUNTER, user_name, pwd);
        System.out.println("USER: " + user_name);
        System.out.println("PASSWORD: " + pwd);
        users.add(user);
        return user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}

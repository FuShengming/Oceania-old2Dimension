package com.old2dimension.OCEANIA.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserQueryResolver implements GraphQLQueryResolver {
    private final UserRepository userRepository;

    public UserQueryResolver(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> get_users() {
        return userRepository.getUsers();
    }
}

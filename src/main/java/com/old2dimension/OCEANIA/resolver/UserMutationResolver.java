package com.old2dimension.OCEANIA.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.User;
import org.springframework.stereotype.Service;

@Service
public class UserMutationResolver implements GraphQLMutationResolver {

    private final UserRepository userRepository;

    public UserMutationResolver(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create_user(final String user_name, final String pwd) {
        return userRepository.createUser(user_name, pwd);
    }
}

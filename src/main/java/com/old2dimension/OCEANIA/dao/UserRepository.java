package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByName(String name);

    public User findUserById(int id);
}

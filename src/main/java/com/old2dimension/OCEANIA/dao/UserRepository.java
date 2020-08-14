package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByName(String name);

    public User findUserById(int id);

    @Query(nativeQuery = true, value = "SELECT * FROM `user` WHERE id IN" +
            "(SELECT user_id FROM team_member WHERE group_id = ?1)")
    public List<User> findUserByGroupId(int groupId);
}

package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    @Query(nativeQuery = true, value = "select * from user_authority, authority"
            + " where user_authority.authority_id = authority.id" + " and user_authority.user_id = ?1")
    public List<Authority> findByUserId(int userId);

    @Modifying
    @Query(nativeQuery = true, value = "insert into user_authority(user_id, authority_id) values(?1, 2)")
    public void insertByUserId(int userId);
}

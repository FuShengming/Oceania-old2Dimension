package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    @Transactional
    void deleteById(int id);
    Group findGroupById(int id);

}

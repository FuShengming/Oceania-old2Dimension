package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface GroupRepository extends JpaRepository<Group,Integer> {
    @Transactional
    void deleteById(int id);
}

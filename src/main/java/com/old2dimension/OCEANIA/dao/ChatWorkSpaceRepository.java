package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.ChatWorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatWorkSpaceRepository extends JpaRepository<ChatWorkSpace,Integer> {
}

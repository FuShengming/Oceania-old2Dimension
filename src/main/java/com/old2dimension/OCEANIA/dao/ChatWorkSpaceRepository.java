package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.ChatWorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatWorkSpaceRepository extends JpaRepository<ChatWorkSpace,Integer> {
    ChatWorkSpace findChatWorkSpaceByUserId(int userId);

    List<ChatWorkSpace> findChatWorkSpacesByUserId(int userId);
}

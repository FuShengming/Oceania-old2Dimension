package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Integer> {
}

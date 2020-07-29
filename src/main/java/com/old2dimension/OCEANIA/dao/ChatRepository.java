package com.old2dimension.OCEANIA.dao;
import com.old2dimension.OCEANIA.po.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatMessage,Integer> {

}

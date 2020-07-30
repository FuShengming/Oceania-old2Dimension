package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Integer> {
    List<ChatMessage> findChatMessagesByRecipientIdAndHasRead(int recipientId,int hasRead);
    List<ChatMessage> findChatMessagesBySenderIdAndRecipientId(int senderId,int recipientId);
    ChatMessage findChatMessagesByRecipientIdAndId(int recipientId,int id);
    int countChatMessagesByRecipientIdAndHasRead(int recipientId,int hasRead);
}

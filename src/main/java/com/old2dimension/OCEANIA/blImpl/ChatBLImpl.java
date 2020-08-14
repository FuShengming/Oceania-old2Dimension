package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.MessageServer.ChatServer;
import com.old2dimension.OCEANIA.bl.ChatBL;
import com.old2dimension.OCEANIA.dao.ChatMessageRepository;
import com.old2dimension.OCEANIA.dao.ChatWorkSpaceRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.ChatMessage;
import com.old2dimension.OCEANIA.po.ChatWorkSpace;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserIdAndMessageIdsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ChatBLImpl implements ChatBL {
    @Autowired
    ChatMessageRepository chatMessageRepository;
    @Autowired
    ChatWorkSpaceRepository chatWorkSpaceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatServer chatServer;


    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setChatMessageRepository(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public void setChatServer(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    public void setChatWorkSpaceRepository(ChatWorkSpaceRepository chatWorkSpaceRepository) {
        this.chatWorkSpaceRepository = chatWorkSpaceRepository;
    }

    @Override
    public ResponseVO sendMessage(ChatMessage chatMessage) {
        if (chatMessage.getId() != 0) {
            return ResponseVO.buildFailure("Chatting message has to be new.");
        }
        if (userRepository.findUserById(chatMessage.getSenderId()) == null ||
                userRepository.findUserById(chatMessage.getRecipientId()) == null) {
            return ResponseVO.buildFailure("user does not exist.");
        }
        chatMessage = chatMessageRepository.save(chatMessage);
        if (chatMessage.getId() == 0) {
            return ResponseVO.buildFailure("Sending message failed.");
        }
        int re = chatMessage.getRecipientId();
        chatServer.sendInfo(re, chatMessageRepository.countChatMessagesByRecipientIdAndHasRead(re, 0));
        return ResponseVO.buildSuccess(chatMessage);
    }

    @Override
    public ResponseVO getUnreadMessage(int userId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByRecipientIdAndHasRead(userId, 0);
        if (chatMessages == null) {
            return ResponseVO.buildFailure("Getting unread messages failed.");
        }

        return ResponseVO.buildSuccess(chatMessages);
    }

    @Override
    public ResponseVO saveWorkSpace(ChatWorkSpace chatWorkSpace) {
        int userId = chatWorkSpace.getUserId();
        ChatWorkSpace cur = chatWorkSpaceRepository.findChatWorkSpaceByUserId(userId);
        if (chatWorkSpace.getId() != 0) {
            if (cur == null) {
                return ResponseVO.buildFailure("Workspace does not exist.");
            }
            if (cur.getId() != chatWorkSpace.getId()) {
                return ResponseVO.buildFailure("Workspace doesn't match user.");
            }
            if (chatWorkSpace.getDate().compareTo(cur.getDate()) < 0) {
                return ResponseVO.buildFailure("Date of workspace has to be after the existed one.");
            }
            chatWorkSpaceRepository.save(chatWorkSpace);
        } else {
            if (cur != null) {
                return ResponseVO.buildFailure("Workspace has to be only one.");
            }

            chatWorkSpace = chatWorkSpaceRepository.save(chatWorkSpace);
            if (chatWorkSpace.getId() == 0) {
                return ResponseVO.buildFailure("Saving workspace failed.");
            }
        }
        return ResponseVO.buildSuccess(chatWorkSpace);
    }

    @Override
    public ResponseVO getChattingRecords(List<Integer> userIds) {
        if (userIds == null || userIds.size() != 2) {
            return ResponseVO.buildFailure("The format of userIds error.");
        }
        List<ChatMessage> chatMessages = chatMessageRepository.
                findChatMessagesBySenderIdAndRecipientId(userIds.get(0), userIds.get(1));
        if (chatMessages == null) {
            return ResponseVO.buildFailure("Finding chatting record failed.");
        }
        return ResponseVO.buildSuccess(chatMessages);
    }

    @Override
    public ResponseVO getWorkSpace(int userId) {
        List<ChatWorkSpace> chatWorkSpace = chatWorkSpaceRepository.findChatWorkSpacesByUserId(userId);
        if (chatWorkSpace == null) {
            return ResponseVO.buildFailure("Getting workspace failed.");
        }
        if (chatWorkSpace.size() == 0) {
            return ResponseVO.buildSuccess("no workspace");
        }
        return ResponseVO.buildSuccess(chatWorkSpace.get(0));
    }

    @Override
    public ResponseVO readMessages(UserIdAndMessageIdsForm userIdAndMessageIdsForm) {
        List<Integer> ids = userIdAndMessageIdsForm.getMessageIds();
        boolean hasRead = false;
        System.out.println(userIdAndMessageIdsForm.getUserId());
        if (ids == null || ids.size() == 0) {
            return ResponseVO.buildSuccess("No message to be read.");
        }
        int userId = userIdAndMessageIdsForm.getUserId();
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (int id : ids) {
            ChatMessage message = chatMessageRepository.findChatMessagesByRecipientIdAndId(userId, id);

            if (message == null) {
                return ResponseVO.buildFailure("This message does not match user.");
            }
            if(message.getHasRead()==0){
                message.setHasRead(1);
                hasRead=true;
            }

            chatMessages.add(message);
        }
        chatMessageRepository.saveAll(chatMessages);
        if(hasRead){
            chatServer.sendInfo(userId, chatMessageRepository.countChatMessagesByRecipientIdAndHasRead(userId, 0));

        }
        return ResponseVO.buildSuccess(ids);
    }

    @Override
    public ResponseVO getUnreadUsers(int userId) {
        Set<Integer> res = new HashSet<>();
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByRecipientIdAndHasRead(userId, 0);
        if (chatMessages == null) {
            return ResponseVO.buildFailure("getting messages failed");
        }
        for (ChatMessage chatMessage : chatMessages) {
            res.add(chatMessage.getSenderId());
        }
        return ResponseVO.buildSuccess(res);
    }
}

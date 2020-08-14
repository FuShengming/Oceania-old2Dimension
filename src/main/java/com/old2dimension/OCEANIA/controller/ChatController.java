package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.ChatBL;
import com.old2dimension.OCEANIA.po.ChatMessage;
import com.old2dimension.OCEANIA.po.ChatWorkSpace;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserIdAndMessageIdsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatBL chatBL;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ResponseVO sendMessage(@RequestBody ChatMessage chatMessage) {
        return chatBL.sendMessage(chatMessage);
    }

    @RequestMapping(value = "/getUnreadMessages/{userId}", method = RequestMethod.GET)
    public ResponseVO getUnreadMessages(@PathVariable int userId) {
        return chatBL.getUnreadMessage(userId);
    }

    @RequestMapping(value = "/saveWorkSpace", method = RequestMethod.POST)
    public ResponseVO saveWorkSpace(@RequestBody ChatWorkSpace chatWorkSpace) {
        return chatBL.saveWorkSpace(chatWorkSpace);
    }

    @RequestMapping(value = "/getChattingRecords", method = RequestMethod.POST)
    public ResponseVO getChattingRecords(@RequestBody List<Integer> userIds) {
        return chatBL.getChattingRecords(userIds);
    }

    @RequestMapping(value = "/getWorkSpace/{userId}", method = RequestMethod.GET)
    public ResponseVO getWorkSpace(@PathVariable int userId) {
        return chatBL.getWorkSpace(userId);
    }

    @RequestMapping(value = "/getUnreadUsers/{userId}", method = RequestMethod.GET)
    public ResponseVO getUnreadUsers(@PathVariable int userId) {
        return chatBL.getUnreadUsers(userId);
    }

    @RequestMapping(value = "/readMessages", method = RequestMethod.POST)
    public ResponseVO readMessages(@RequestBody UserIdAndMessageIdsForm userIdAndMessageIdsForm) {
        return chatBL.readMessages(userIdAndMessageIdsForm);
    }

}

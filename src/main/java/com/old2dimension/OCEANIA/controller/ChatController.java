package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.po.ChatMessage;
import com.old2dimension.OCEANIA.po.ChatWorkSpace;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserIdAndMessageIdsForm;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @RequestMapping(value = "/sendMessage",method = RequestMethod.POST)
    public ResponseVO sendMessage(@RequestBody ChatMessage chatMessage){return null;}

    @RequestMapping(value = "/getUnreadMessage/{userId}",method = RequestMethod.GET)
    public ResponseVO getUnreadMessage(@PathVariable int userId){return null;}

    @RequestMapping(value = "/saveWorkSpace",method = RequestMethod.POST)
    public ResponseVO saveWorkSpace(@RequestBody ChatWorkSpace chatWorkSpace){return null;}

    @RequestMapping(value = "/getChattingRecords",method = RequestMethod.POST)
    public ResponseVO getChattingRecords(@RequestBody List<Integer> userIds){return null;}

    @RequestMapping(value = "/getWorkSpace/{userId}",method = RequestMethod.GET)
    public ResponseVO getWorkSpace(@PathVariable int userId){return null;}


    @RequestMapping(value = "/readMessages",method = RequestMethod.POST)
    public ResponseVO readMessages(UserIdAndMessageIdsForm userIdAndMessageIdsForm){return null;}

}

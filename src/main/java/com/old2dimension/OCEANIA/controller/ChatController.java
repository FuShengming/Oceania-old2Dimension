package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.po.ChatMessage;
import com.old2dimension.OCEANIA.po.ChatWorkSpace;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @RequestMapping(value = "/sendMessage",method = RequestMethod.POST)
    public ResponseVO sendMessage(@RequestBody ChatMessage chatMessage){return null;}

    @RequestMapping(value = "/getUnreadMessage",method = RequestMethod.POST)
    public ResponseVO getUnreadMessage(int userId){return null;}

    @RequestMapping(value = "/saveWorkSpace",method = RequestMethod.POST)
    public ResponseVO saveWorkSpace(@RequestBody ChatWorkSpace chatWorkSpace){return null;}

    @RequestMapping(value = "/getChattingRecords",method = RequestMethod.POST)
    public ResponseVO getChattingRecords(@RequestBody List<Integer> userAnd){return null;}

    @RequestMapping(value = "/getWorkSpace",method = RequestMethod.POST)
    public ResponseVO getWorkSpace(int userId){return null;}


}

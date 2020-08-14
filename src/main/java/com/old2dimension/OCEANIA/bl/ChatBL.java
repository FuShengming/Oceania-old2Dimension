package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.ChatMessage;
import com.old2dimension.OCEANIA.po.ChatWorkSpace;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserIdAndMessageIdsForm;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
public interface ChatBL {

    public ResponseVO sendMessage(ChatMessage chatMessage);


    public ResponseVO getUnreadMessage(int userId);


    public ResponseVO saveWorkSpace(ChatWorkSpace chatWorkSpace);


    public ResponseVO getChattingRecords(List<Integer> userIds);


    public ResponseVO getWorkSpace(int userId);

    public ResponseVO readMessages(UserIdAndMessageIdsForm userIdAndMessageIdsForm);

    public ResponseVO getUnreadUsers(int userId);
}

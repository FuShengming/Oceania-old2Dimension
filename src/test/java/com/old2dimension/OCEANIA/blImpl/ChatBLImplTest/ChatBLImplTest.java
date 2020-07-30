package com.old2dimension.OCEANIA.blImpl.ChatBLImplTest;

import com.old2dimension.OCEANIA.MessageServer.ChatServer;
import com.old2dimension.OCEANIA.bl.ChatBL;
import com.old2dimension.OCEANIA.blImpl.ChatBLImpl;
import com.old2dimension.OCEANIA.dao.ChatMessageRepository;
import com.old2dimension.OCEANIA.dao.ChatWorkSpaceRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.ChatMessage;
import com.old2dimension.OCEANIA.po.ChatWorkSpace;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.po.WorkSpace;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
public class ChatBLImplTest {

    @Test
    void SendMessageTest1(){
        ChatBLImpl chatBL= new ChatBLImpl();

        ChatMessageRepository chatMessageRepository = mock(ChatMessageRepository.class);
        ChatWorkSpaceRepository chatWorkSpaceRepository = mock(ChatWorkSpaceRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        ChatServer chatServer = mock(ChatServer.class);

        chatBL.setChatMessageRepository(chatMessageRepository);
        chatBL.setUserRepository(userRepository);
        chatBL.setChatServer(chatServer);
        chatBL.setChatWorkSpaceRepository(chatWorkSpaceRepository);

        ChatMessage chatMessage = new ChatMessage(-1,-2,new Date(),0);
        ChatMessage chatMessage1 = new ChatMessage(-1,-2,new Date(),0);
        chatMessage1.setId(-1);
        User user1 = new User();
        user1.setId(-1);
        User user2 = new User();
        user2.setId(-2);

        when(userRepository.findUserById(-1)).thenReturn(user1);
        when(userRepository.findUserById(-2)).thenReturn(user2);
        when(chatMessageRepository.save(any())).thenReturn(chatMessage1);
        when(chatMessageRepository.countChatMessagesByRecipientIdAndHasRead(-2,0)).thenReturn(0);

        ResponseVO res = chatBL.sendMessage(chatMessage);

        Assert.assertEquals(((ChatMessage)res.getContent()).getId(),-1);


    }

    @Test
    void sendMessageTest2(){
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatMessage chatMessage = new ChatMessage(-1,-2,new Date(),0);
        chatMessage.setId(-1);
        ResponseVO res = chatBL.sendMessage(chatMessage);
        Assert.assertEquals(res.getMessage(),"Chatting message has to be new.");
    }

    @Test
    void sendMessageTest3(){
        ChatBLImpl chatBL= new ChatBLImpl();


        UserRepository userRepository = mock(UserRepository.class);

        chatBL.setUserRepository(userRepository);

        when(userRepository.findUserById(-1)).thenReturn(null);
        when(userRepository.findUserById(-2)).thenReturn(null);

        ChatMessage chatMessage = new ChatMessage(-1,-2,new Date(),0);

        ResponseVO res = chatBL.sendMessage(chatMessage);
        Assert.assertEquals(res.getMessage(),"user does not exist.");
    }

    @Test
    void sendMessageTest4(){
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatMessageRepository chatMessageRepository = mock(ChatMessageRepository.class);
        ChatWorkSpaceRepository chatWorkSpaceRepository = mock(ChatWorkSpaceRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        ChatServer chatServer = mock(ChatServer.class);

        chatBL.setChatMessageRepository(chatMessageRepository);
        chatBL.setUserRepository(userRepository);
        chatBL.setChatServer(chatServer);
        chatBL.setChatWorkSpaceRepository(chatWorkSpaceRepository);

        ChatMessage chatMessage = new ChatMessage(-1,-2,new Date(),0);

        User user1 = new User();
        user1.setId(-1);
        User user2 = new User();
        user2.setId(-2);

        when(userRepository.findUserById(-1)).thenReturn(user1);
        when(userRepository.findUserById(-2)).thenReturn(user2);
        when(chatMessageRepository.save(any())).thenReturn(chatMessage);


        ResponseVO res = chatBL.sendMessage(chatMessage);

        Assert.assertEquals(res.getMessage(),"Sending message failed.");
    }

    @Test
    void getUnreadMessageTest1(){
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatMessageRepository chatMessageRepository = mock(ChatMessageRepository.class);
        chatBL.setChatMessageRepository(chatMessageRepository);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(-1);
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(chatMessage);
        when(chatMessageRepository.findChatMessagesByRecipientIdAndHasRead(-1,0)).thenReturn(chatMessages);

        ResponseVO res = chatBL.getUnreadMessage(-1);
        Assert.assertEquals(((List<ChatMessage>)res.getContent()).size(),1);
        Assert.assertEquals(((List<ChatMessage>)res.getContent()).get(0).getId(),-1);
    }

    @Test
    void getUnreadMessageTest2(){
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatMessageRepository chatMessageRepository = mock(ChatMessageRepository.class);
        chatBL.setChatMessageRepository(chatMessageRepository);

        when(chatMessageRepository.findChatMessagesByRecipientIdAndHasRead(-1,0)).thenReturn(null);

        ResponseVO res = chatBL.getUnreadMessage(-1);

        Assert.assertEquals(res.getMessage(),"Getting unread messages failed.");
    }

    @Test
    void saveWorkSpaceTest1(){
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatWorkSpaceRepository chatWorkSpaceRepository = mock(ChatWorkSpaceRepository.class);
        chatBL.setChatWorkSpaceRepository(chatWorkSpaceRepository);


        ChatWorkSpace chatWorkSpace = new ChatWorkSpace(-1,"",new Date());
        ChatWorkSpace reCws = new ChatWorkSpace();
        chatWorkSpace.setId(-1);

        when(chatWorkSpaceRepository.findChatWorkSpaceByUserId(-1)).thenReturn(null);

        ResponseVO res = chatBL.saveWorkSpace(chatWorkSpace);
        Assert.assertEquals(res.getMessage(),"Workspace does not exist.");


    }

    @Test
    void saveWorkSpaceTest2(){
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatWorkSpaceRepository chatWorkSpaceRepository = mock(ChatWorkSpaceRepository.class);
        chatBL.setChatWorkSpaceRepository(chatWorkSpaceRepository);


        ChatWorkSpace chatWorkSpace = new ChatWorkSpace(-1,"",new Date());
        ChatWorkSpace reCws = new ChatWorkSpace(-1,"",new Date());
        reCws.setId(-2);
        chatWorkSpace.setId(-1);

        when(chatWorkSpaceRepository.findChatWorkSpaceByUserId(-1)).thenReturn(reCws);

        ResponseVO res = chatBL.saveWorkSpace(chatWorkSpace);
        Assert.assertEquals(res.getMessage(),"Workspace doesn't match user.");


    }

    @Test
    void saveWorkSpaceTest3() throws ParseException {
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatWorkSpaceRepository chatWorkSpaceRepository = mock(ChatWorkSpaceRepository.class);
        chatBL.setChatWorkSpaceRepository(chatWorkSpaceRepository);


        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1 = dateFormat1.parse("2009-06-01");
        ChatWorkSpace chatWorkSpace = new ChatWorkSpace(-1,"",myDate1);
        chatWorkSpace.setId(-1);
        myDate1 = dateFormat1.parse("2009-06-02");
        ChatWorkSpace reCws = new ChatWorkSpace(-1,"",myDate1);
        reCws.setId(-1);

        when(chatWorkSpaceRepository.findChatWorkSpaceByUserId(-1)).thenReturn(reCws);

        ResponseVO res = chatBL.saveWorkSpace(chatWorkSpace);
        Assert.assertEquals(res.getMessage(),"Date of workspace has to be after the existed one.");


    }
    @Test
    void saveWorkSpaceTest4()  {
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatWorkSpaceRepository chatWorkSpaceRepository = mock(ChatWorkSpaceRepository.class);
        chatBL.setChatWorkSpaceRepository(chatWorkSpaceRepository);


        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

        ChatWorkSpace chatWorkSpace = new ChatWorkSpace(-1,"",new Date());
        chatWorkSpace.setId(0);

        ChatWorkSpace reCws = new ChatWorkSpace(-1,"",new Date());
        reCws.setId(-1);

        when(chatWorkSpaceRepository.findChatWorkSpaceByUserId(-1)).thenReturn(reCws);

        ResponseVO res = chatBL.saveWorkSpace(chatWorkSpace);
        Assert.assertEquals(res.getMessage(),"Workspace has to be only one.");


    }

    @Test
    void saveWorkSpaceTest5()  {
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatWorkSpaceRepository chatWorkSpaceRepository = mock(ChatWorkSpaceRepository.class);
        chatBL.setChatWorkSpaceRepository(chatWorkSpaceRepository);

        ChatWorkSpace chatWorkSpace = new ChatWorkSpace(-1,"",new Date());
        chatWorkSpace.setId(0);

        when(chatWorkSpaceRepository.findChatWorkSpaceByUserId(-1)).thenReturn(null);
        when(chatWorkSpaceRepository.save(any())).thenReturn(chatWorkSpace);
        ResponseVO res = chatBL.saveWorkSpace(chatWorkSpace);
        Assert.assertEquals(res.getMessage(),"Saving workspace failed.");

    }

    @Test
    void saveWorkSpaceTest6()  {
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatWorkSpaceRepository chatWorkSpaceRepository = mock(ChatWorkSpaceRepository.class);
        chatBL.setChatWorkSpaceRepository(chatWorkSpaceRepository);

        ChatWorkSpace chatWorkSpace = new ChatWorkSpace(-1,"",new Date());
        chatWorkSpace.setId(0);

        ChatWorkSpace chatWorkSpace1 = new ChatWorkSpace(-1,"",new Date());
        chatWorkSpace1.setId(-1);
        when(chatWorkSpaceRepository.findChatWorkSpaceByUserId(-1)).thenReturn(null);
        when(chatWorkSpaceRepository.save(any())).thenReturn(chatWorkSpace1);
        ResponseVO res = chatBL.saveWorkSpace(chatWorkSpace);
        Assert.assertEquals(((ChatWorkSpace)res.getContent()).getId(),-1);

    }

    @Test
    void saveWorkSpaceTest7() throws ParseException {
        ChatBLImpl chatBL= new ChatBLImpl();
        ChatWorkSpaceRepository chatWorkSpaceRepository = mock(ChatWorkSpaceRepository.class);
        chatBL.setChatWorkSpaceRepository(chatWorkSpaceRepository);


        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1 = dateFormat1.parse("2009-06-02");
        ChatWorkSpace chatWorkSpace = new ChatWorkSpace(-1,"",myDate1);
        chatWorkSpace.setId(-1);
        myDate1 = dateFormat1.parse("2009-06-01");
        ChatWorkSpace reCws = new ChatWorkSpace(-1,"",myDate1);
        reCws.setId(-1);

        when(chatWorkSpaceRepository.findChatWorkSpaceByUserId(-1)).thenReturn(reCws);

        ResponseVO res = chatBL.saveWorkSpace(chatWorkSpace);
        Assert.assertEquals(((ChatWorkSpace)res.getContent()).getId(),-1);


    }

    @Test
    void getChattingRecordsTest1(){
        ChatBLImpl chatBL = new ChatBLImpl();
    }

}

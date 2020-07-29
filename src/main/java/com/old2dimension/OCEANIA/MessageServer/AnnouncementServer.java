package com.old2dimension.OCEANIA.MessageServer;

import com.old2dimension.OCEANIA.Encoder.AnnouncementListEncoder;
import com.old2dimension.OCEANIA.dao.AnnouncementReadRepository;
import com.old2dimension.OCEANIA.dao.AnnouncementRepository;
import com.old2dimension.OCEANIA.po.Announcement;
import com.old2dimension.OCEANIA.po.AnnouncementRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/websocket/announcement/{userId}",encoders = { AnnouncementListEncoder.class })
@Component
public class AnnouncementServer {

    private static ApplicationContext applicationContext;
    //你要注入的service或者dao

//    public static void setApplicationContext(ApplicationContext applicationContext) {
//        AnnouncementServer.applicationContext = applicationContext;
//    }
    @Autowired
    public void setAnnouncementReadRepository(AnnouncementReadRepository announcementReadRepository){
        AnnouncementServer.announcementReadRepository = announcementReadRepository;
    }
    @Autowired
    public void setAnnouncementRepository(AnnouncementRepository announcementRepository){
        AnnouncementServer.announcementRepository = announcementRepository;
    }
    private static AnnouncementRepository announcementRepository;

    private static AnnouncementReadRepository announcementReadRepository;


    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<Integer, Session> sessionPools = new ConcurrentHashMap<>();

    //发送消息
    public void sendMessage(Session session, Object message) throws IOException, EncodeException {
        if(session != null){
            synchronized (session) {
//                System.out.println("发送数据：" + message);

                session.getBasicRemote().sendObject(message);
            }
        }
    }
    //给指定用户发送信息
    public void sendInfo(int userId, List<Announcement> message){
        Session session = sessionPools.get(userId);
        if(session==null){
            return;
        }

        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") int userId){
        sessionPools.put(userId,session);
        addOnlineCount();
        System.out.println(userId + "加入webSocket！当前人数为" + onlineNum);
//        announcementRepository = applicationContext.getBean(announcementRepository.getClass());
//        announcementReadRepository = applicationContext.getBean(announcementReadRepository.getClass());
        List<AnnouncementRead> announcementReads = announcementReadRepository.findAnnouncementReadsByUserIdAndHasRead(userId,0);

        List<Integer> ids = new ArrayList<>();
        for(AnnouncementRead a:announcementReads){
            ids.add(a.getAnnouncementId());
        }
        List<Announcement> message = announcementRepository.findAllById(ids);
        try {
            sendMessage(session, message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "userId") int userId){
        sessionPools.remove(userId);
        subOnlineCount();
        System.out.println(userId + "断开webSocket连接！当前人数为" + onlineNum);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message) throws IOException{
        message = "客户端：" + message + ",已收到";
        System.out.println(message);
        for (Session session: sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }
}

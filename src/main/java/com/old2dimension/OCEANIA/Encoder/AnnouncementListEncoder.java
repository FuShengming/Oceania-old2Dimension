package com.old2dimension.OCEANIA.Encoder;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.old2dimension.OCEANIA.po.Announcement;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.ArrayList;
import java.util.List;
@Component
public class AnnouncementListEncoder implements Encoder.Text<List<Announcement>> {
    @Override
    public String encode(List<Announcement> announcements) throws EncodeException {
        return JSON.toJSONString(announcements);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}

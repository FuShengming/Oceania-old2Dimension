package com.old2dimension.OCEANIA.Encoder;

import com.alibaba.fastjson.JSON;
import com.old2dimension.OCEANIA.po.Invitation;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.List;

public class InvitationListEncoder implements Encoder.Text<List<Invitation>> {
@Override
public String encode(List<Invitation> invitations) throws EncodeException {
        return JSON.toJSONString(invitations);
        }


    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

        }
}

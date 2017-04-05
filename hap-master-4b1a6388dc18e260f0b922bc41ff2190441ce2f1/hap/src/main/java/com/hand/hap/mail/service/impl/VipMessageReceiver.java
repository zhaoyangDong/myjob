package com.hand.hap.mail.service.impl;

import com.hand.hap.mail.dto.Message;
import com.hand.hap.mail.service.IEmailService;
import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.QueueMonitor;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jialong.zuo@hand-china on 2016/12/8.
 */
@Service
@QueueMonitor(queue="hap:queue:email:vip")
public class VipMessageReceiver implements IMessageConsumer<Message> {
    @Autowired
    IEmailService emailService;
    @Override
    public void onMessage(Message message, String pattern) {
        Map params=new HashedMap();
        params.put("batch",0);
        params.put("isVipQueue",true);

        try {
            emailService.sendMessageByReceiver(message,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

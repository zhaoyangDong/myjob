package com.hand.hap.mail.controllers;

import com.hand.hap.mail.dto.Message;
import com.hand.hap.mail.mapper.MessageMapper;
import com.hand.hap.mail.service.IEmailService;
import com.hand.hap.mail.service.IMessageService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zjl on 2016/11/22.
 */
@Controller
public class EmailController extends BaseController {

    @Autowired
    IEmailService emailService;

    @Autowired
    MessageMapper messageMapper;

    @RequestMapping(value = "/send_all_email")
    @ResponseBody
    public boolean sendAllEmail() {
        Map pa = new HashedMap();
        pa.put("batch", 0);
        pa.put("isVipQueue", false);
        boolean result = false;
        try {
            result = emailService.sendMessages(pa);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/mail/resend_email")
    @ResponseBody
    public boolean reSendEmail(@RequestBody List<Message> messages) {
        boolean result = false;
        List<Message> newMessages = new ArrayList<>();
        for (Message mess : messages) {
            Message s1 = messageMapper.selectByPrimaryKey(mess);
            if (s1 != null && s1.getSendFlag().equals("F")) {
                newMessages.add(s1);
            }
        }
        try {
            result = emailService.reSendMessages(newMessages, new HashedMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

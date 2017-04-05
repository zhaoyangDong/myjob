/*
 * #{copyright}#
 */

package com.hand.hap.mail.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.mail.dto.MessageTransaction;
import com.hand.hap.mail.mapper.MessageTransactionMapper;
import com.hand.hap.mail.service.IEmailService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.mail.PriorityLevelEnum;
import com.hand.hap.mail.ReceiverTypeEnum;
import com.hand.hap.mail.dto.Message;
import com.hand.hap.mail.dto.MessageReceiver;
import com.hand.hap.mail.service.IMessageService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * 对消息的操作.
 * 
 * @author xiawang.liu@hand-china.com
 */

@Controller
public class MessageController extends BaseController {

    @Autowired
    private IMessageService messageService;

    @Autowired
    MessageTransactionMapper messageTransactionMapper;
    /**
     * 查询消息.
     * 
     * @param request
     *            HttpServletRequest
     * @param message
     *            Message
     * @param page
     *            page
     * @param pagesize
     *            pagesize
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/message/query")
    @ResponseBody
    public ResponseData getMessageBySubject(HttpServletRequest request, Message message,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(messageService.selectMessagesBySubject(requestContext, message, page, pagesize));
    }

    /**
     * 查询消息地址.
     * 
     * @param request
     *            HttpServletRequest
     * @param messageReceiver
     *            MessageReceiver
     * @param page
     *            page
     * @param pagesize
     *            pagesize
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/message/queryMessageAddresses")
    @ResponseBody
    public ResponseData getMessageAddressesByMessageId(HttpServletRequest request, MessageReceiver messageReceiver,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                messageService.selectMessageAddressesByMessageId(requestContext, messageReceiver, page, pagesize));
    }

    @RequestMapping(value = "/sys/message/sendTest")
    @ResponseBody
    public ResponseData sendTestMessage(HttpServletRequest request, @RequestBody Map<String, Object> param) throws
            Exception {
        IRequest iRequest = createRequestContext(request);
        String str = param.get("receivers").toString();
        String [] receivers =StringUtils.split(str, ";");
        ArrayList<MessageReceiver> receiverList = new ArrayList<>();
        for(String r:receivers) {
            MessageReceiver mr = new MessageReceiver();
            mr.setMessageAddress(r);
            mr.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
            receiverList.add(mr);
        }

        List<Integer> attachments=(List<Integer>)param.get("attachments");
        List<Long> attachment=null;
        if(null!=attachments){
            attachment=new ArrayList<>();
            for(Integer s:attachments){
                attachment.add(Long.valueOf(s));
            }
        }

        if(param.get("mode").equals("custom")){
            messageService.addEmailMessage((Long)iRequest.getUserId(), param.get("accountCode").toString(),
                    param.get("subject").toString(), param.get("content").toString(), PriorityLevelEnum.NORMAL,attachment,receiverList);
        }else{
            messageService.addEmailMessage((Long)iRequest.getUserId(),param.get("accountCode").toString(),param.get("templateCode").toString(),null,attachment,receiverList);
        }

        return new ResponseData();
    }

    @RequestMapping(value = "/message/error_mess")
    @ResponseBody
    public String errorMess(int messageId){
        Long id=new Long((long)messageId);
       MessageTransaction messageTransaction=new MessageTransaction();
        messageTransaction.setMessageId(id);
        List<MessageTransaction> mess=messageTransactionMapper.select(messageTransaction);
        String result=mess.get(mess.size()-1).getTransactionMessage();
         return result;
    }
}
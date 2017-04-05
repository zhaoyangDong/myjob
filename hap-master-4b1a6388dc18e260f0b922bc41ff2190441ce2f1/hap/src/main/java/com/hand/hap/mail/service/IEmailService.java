/*
 * #{copyright}#
 */
package com.hand.hap.mail.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.mail.dto.Message;
import com.hand.hap.mail.dto.MessageTransaction;

/**
 * email服务,被job调用.
 * 
 * @author Clerifen Li
 */
public interface IEmailService extends ProxySelf<IEmailService> {

    /*
     * 定时器定时发送邮件 已经在内部实现事务,有新的实现修改内部代码
     * 
     * @throws Exception
     */

    boolean sendMessages(Map<String, Object> params)throws Exception;

    boolean reSendMessages(List<Message> messages,Map<String,Object> params)throws Exception;

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    void saveTransaction(Message message, MessageTransaction obj);

    boolean sendMessageByReceiver(Message message,Map<String,Object> params)throws Exception;
}

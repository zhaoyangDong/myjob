/*
 * #{copyright}#
 */
package com.hand.hap.mail.mapper;

import java.util.List;

import com.hand.hap.mail.dto.Message;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author Clerifen Li
 * @author xiawang.liu@hand-china.com
 */
public interface MessageMapper extends Mapper<Message> {

    /*
     * 没有发出的邮件列表,普通队列
     */
    List<Message> selectEmailToSend();
    
    /*
     * 没有发出的邮件列表,优先队列
     */
    List<Message> selectVipEmailToSend();
    
    /*
     * 根据subject查询消息
     */
    List<Message> selectMessagesBySubject(Message message);
}
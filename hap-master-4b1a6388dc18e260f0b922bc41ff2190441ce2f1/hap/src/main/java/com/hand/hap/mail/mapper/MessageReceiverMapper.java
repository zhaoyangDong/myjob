/*
 * #{copyright}#
 */

package com.hand.hap.mail.mapper;

import java.util.List;

import com.hand.hap.mail.dto.MessageReceiver;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author chuangsheng.zhang
 * @author xiawang.liu@hand-china.com
 */
public interface MessageReceiverMapper extends Mapper<MessageReceiver> {

    int deleteByMessageId(Long messageId);

    List<MessageReceiver> selectByMessageId(Long messageId);

    /*
     * 根据MessageId查询消息地址
     */
    List<MessageReceiver> selectMessageAddressesByMessageId(MessageReceiver messageReceiver);
}
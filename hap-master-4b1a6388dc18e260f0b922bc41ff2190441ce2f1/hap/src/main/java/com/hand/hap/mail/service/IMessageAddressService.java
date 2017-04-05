/*
 * #{copyright}#
 */
package com.hand.hap.mail.service;

import java.util.List;

import com.hand.hap.mail.dto.MessageAddress;
import com.hand.hap.mail.MessageTypeEnum;

/**
 * @author shiliyan
 *
 */
public interface IMessageAddressService {
    /**
     * 
     * 返回消息地址列表.
     * 
     * @param msgType
     *            消息类型 MessageTypeEnum
     * @return 地址列表
     */
    List<String> queryMessageAddress(MessageTypeEnum msgType, MessageAddress address);
}

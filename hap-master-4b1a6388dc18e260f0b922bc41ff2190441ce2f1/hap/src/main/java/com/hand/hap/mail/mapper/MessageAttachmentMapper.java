/*
 * #{copyright}#
 */

package com.hand.hap.mail.mapper;

import java.util.List;

import com.hand.hap.mail.dto.MessageAttachment;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author chuangshneg.zhang
 */
public interface MessageAttachmentMapper extends Mapper<MessageAttachment> {

    int deleteByMessageId(Long messageId);

    List<MessageAttachment> selectByMessageId(Long messageId);
}
/*
 * #{copyright}#
 */

package com.hand.hap.mail.mapper;

import com.hand.hap.mail.dto.MessageTransaction;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author chuangsheng.zhang
 */
public interface MessageTransactionMapper extends Mapper<MessageTransaction> {

    int deleteByMessageId(Long messageId);

    long selectSuccessCountByMessageId(Long messageId);
}
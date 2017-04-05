/*
 * #{copyright}#
 */

package com.hand.hap.mail.mapper;

import java.util.List;

import com.hand.hap.mail.dto.MessageAccount;
import com.hand.hap.mybatis.common.Mapper;

public interface MessageAccountMapper extends Mapper<MessageAccount> {

    MessageAccount selectByUniqueCode(String accountCode);

    List<MessageAccount> selectMessageAccountPassword(MessageAccount example);
}
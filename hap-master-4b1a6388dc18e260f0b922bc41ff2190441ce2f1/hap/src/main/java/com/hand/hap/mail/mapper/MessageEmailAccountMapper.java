/*
 * #{copyright}#
 */
package com.hand.hap.mail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mail.dto.MessageEmailAccount;
import com.hand.hap.mail.dto.MessageEmailAccountVo;
import com.hand.hap.mybatis.common.Mapper;


/**
 * @author Clerifen Li
 */
public interface MessageEmailAccountMapper extends Mapper<MessageEmailAccount> {
    
    int deleteByConfigId(Long configId);
    
    List<MessageEmailAccountVo> selectMessageEmailAccounts(MessageEmailAccount record);

    List<MessageEmailAccount> selectMessageEmailAccountPassword(MessageEmailAccount record);
    
    MessageEmailAccount selectByAccountCode(String accountCode);
    
    MessageEmailAccount getMsgEmailAccountByCode(@Param("accountId") Long accountId,
            @Param("accountCode") String accountCode);
}
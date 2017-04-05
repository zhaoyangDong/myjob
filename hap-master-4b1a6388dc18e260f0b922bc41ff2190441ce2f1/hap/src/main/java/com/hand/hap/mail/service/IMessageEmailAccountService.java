/*
 * #{copyright}#
 */
package com.hand.hap.mail.service;

import java.util.List;

import com.hand.hap.mail.dto.MessageEmailAccount;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.mail.dto.MessageEmailAccountVo;

/**
 * 邮件账号服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageEmailAccountService extends ProxySelf<IMessageEmailAccountService> {

    MessageEmailAccount createMessageEmailAccount(IRequest request, @StdWho MessageEmailAccount obj) throws BaseException;

    MessageEmailAccount updateMessageEmailAccount(IRequest request, @StdWho MessageEmailAccount obj);

    MessageEmailAccount updateMessageEmailAccountPasswordOnly(IRequest request, MessageEmailAccount obj);
    
    MessageEmailAccount selectMessageEmailAccountById(IRequest request, Long objId);

    List<MessageEmailAccountVo> selectMessageEmailAccounts(IRequest request, MessageEmailAccount obj, int page, int pageSize);

    int deleteMessageEmailAccount(IRequest request, MessageEmailAccount obj);

    int batchDelete(IRequest request, List<MessageEmailAccount> objs) throws BaseException;
    
    List<MessageEmailAccount> selectMessageEmailAccountWithPassword(IRequest request, MessageEmailAccount obj, int page, int pageSize);
    
}

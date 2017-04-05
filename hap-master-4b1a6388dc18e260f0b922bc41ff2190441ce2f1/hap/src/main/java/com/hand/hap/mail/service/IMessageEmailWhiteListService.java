/*
 * #{copyright}#
 */
package com.hand.hap.mail.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mail.dto.MessageEmailWhiteList;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.ProxySelf;

/**
 * 邮箱白名单服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageEmailWhiteListService extends ProxySelf<IMessageEmailWhiteListService> {

    MessageEmailWhiteList createMessageEmailWhiteList(IRequest request, @StdWho MessageEmailWhiteList obj) throws BaseException;

    MessageEmailWhiteList updateMessageEmailWhiteList(IRequest request, @StdWho MessageEmailWhiteList obj);

    MessageEmailWhiteList selectMessageEmailWhiteListById(IRequest request, Long objId);

    List<MessageEmailWhiteList> selectMessageEmailWhiteLists(IRequest request, MessageEmailWhiteList obj, int page, int pageSize);

    int deleteMessageEmailWhiteList(IRequest request, MessageEmailWhiteList obj);

    int batchDelete(IRequest request, List<MessageEmailWhiteList> objs) throws BaseException;
    
}

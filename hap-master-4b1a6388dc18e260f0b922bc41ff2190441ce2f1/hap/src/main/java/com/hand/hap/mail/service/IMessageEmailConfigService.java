/*
 * #{copyright}#
 */
package com.hand.hap.mail.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.exception.BaseException;
import com.hand.hap.core.exception.EmailException;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.mail.dto.MessageEmailConfig;

/**
 * 邮箱配置服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageEmailConfigService extends ProxySelf<IMessageEmailConfigService> {

    MessageEmailConfig createMessageEmailConfig(IRequest request, @StdWho MessageEmailConfig obj) throws BaseException;

    MessageEmailConfig updateMessageEmailConfig(IRequest request, @StdWho MessageEmailConfig obj);

    MessageEmailConfig selectMessageEmailConfigById(IRequest request, Long objId);

    List<MessageEmailConfig> selectMessageEmailConfigs(IRequest request, MessageEmailConfig obj, int page, int pageSize);

    int deleteMessageEmailConfig(IRequest request, MessageEmailConfig obj);

    int batchDelete(IRequest request, List<MessageEmailConfig> objs) throws BaseException;

    void batchUpdate(IRequest requestContext, @StdWho MessageEmailConfig obj) throws EmailException;

    /**
     * 查询邮箱配置数量.
     * 
     * @param request
     *            上下文
     * @return map 结果
     */
    Map<String, Object> queryMsgConfigQuanties(IRequest request);
}

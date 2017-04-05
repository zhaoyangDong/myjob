/*
 * #{copyright}#
 */
package com.hand.hap.mail.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mail.dto.MessageTemplate;
import com.hand.hap.core.exception.EmailException;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.ProxySelf;

/**
 * 消息模板服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageTemplateService extends ProxySelf<IMessageTemplateService> {

    MessageTemplate createMessageTemplate(IRequest request, @StdWho MessageTemplate obj) throws EmailException;

    MessageTemplate updateMessageTemplate(IRequest request, @StdWho MessageTemplate obj) throws EmailException;

    MessageTemplate selectMessageTemplateById(IRequest request, Long objId);

    List<MessageTemplate> selectMessageTemplates(IRequest request, MessageTemplate obj, int page, int pageSize);

    int deleteMessageTemplate(IRequest request, MessageTemplate obj);

    int batchDelete(IRequest request, List<MessageTemplate> objs);

}

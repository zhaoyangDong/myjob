/*
 * #{copyright}#
 */
package com.hand.hap.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.core.IRequest;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.system.dto.Language;
import com.hand.hap.system.service.ILanguageService;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class LanguageServiceImpl extends BaseServiceImpl<Language> implements ILanguageService {

    @Autowired
    private IMessagePublisher messagePublisher;

    private void notifyCache() {
        messagePublisher.publish("cache.language", "language");
    }

    @Override
    public Language insertSelective(IRequest request, Language lang) {
        Language language = super.insertSelective(request, lang);
        notifyCache();
        return language;
    }

    @Override
    public Language updateByPrimaryKeySelective(IRequest request, Language lang) {
        Language language = super.updateByPrimaryKeySelective(request, lang);
        notifyCache();
        return language;
    }

    @Override
    public int deleteByPrimaryKey(Language lang) {
        int ret = super.deleteByPrimaryKey(lang);
        notifyCache();
        return ret;
    }
}

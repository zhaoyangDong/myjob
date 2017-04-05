/*
 * #{copyright}#
 */
package com.hand.hap.system.service.impl;

import org.springframework.stereotype.Service;

import com.hand.hap.cache.CacheDelete;
import com.hand.hap.cache.CacheSet;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.Prompt;
import com.hand.hap.system.service.IPromptService;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class PromptServiceImpl extends BaseServiceImpl<Prompt> implements IPromptService {

    @Override
    @CacheSet(cache = "prompt")
    public Prompt insertSelective(IRequest request, Prompt prompt) {
        super.insertSelective(request, prompt);
        return prompt;
    }

    @Override
    @CacheDelete(cache = "prompt")
    public int deleteByPrimaryKey(Prompt prompt) {
        return super.deleteByPrimaryKey(prompt);
    }

    @Override
    @CacheSet(cache = "prompt")
    public Prompt updateByPrimaryKeySelective(IRequest request, Prompt prompt) {
        super.updateByPrimaryKeySelective(request, prompt);
        return prompt;
    }
}

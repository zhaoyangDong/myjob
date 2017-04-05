/*
 * #{copyright}#
 */
package com.hand.hap.system.service.impl;

import org.springframework.stereotype.Service;

import com.hand.hap.cache.CacheDelete;
import com.hand.hap.cache.CacheSet;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.SysUserDemo;
import com.hand.hap.system.dto.Prompt;
import com.hand.hap.system.service.IPromptService;
import com.hand.hap.system.service.ISysUserDemoService;

/**
 * @author hailin.xu@hand-china.com
 */
@Service
public class SysUserDemoServiceImpl extends BaseServiceImpl<SysUserDemo> implements ISysUserDemoService {

    @Override
    @CacheSet(cache = "SysDemo")
    public SysUserDemo insertSelective(IRequest request, SysUserDemo sysDemo) {
        super.insertSelective(request, sysDemo);
        return sysDemo;
    }

    @Override
    @CacheDelete(cache = "SysDemo")
    public int deleteByPrimaryKey(SysUserDemo sysDemo) {
        return super.deleteByPrimaryKey(sysDemo);
    }

    @Override
    @CacheSet(cache = "SysDemo")
    public SysUserDemo updateByPrimaryKeySelective(IRequest request, SysUserDemo sysDemo) {
        super.updateByPrimaryKeySelective(request, sysDemo);
        return sysDemo;
    }
}

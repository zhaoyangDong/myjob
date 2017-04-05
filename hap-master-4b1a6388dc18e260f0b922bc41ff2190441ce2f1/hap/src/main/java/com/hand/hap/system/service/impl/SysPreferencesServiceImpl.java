/*
 * #{copyright}#
 */
package com.hand.hap.system.service.impl;

import java.util.List;

import com.hand.hap.system.mapper.SysPreferencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.SysPreferences;
import com.hand.hap.system.service.ISysPreferencesService;

/**
 * 系统首选项service.
 * 
 * @author zhangYang
 * TODO: 增加缓存
 */
@Service
@Transactional
public class SysPreferencesServiceImpl implements ISysPreferencesService {
    private Logger logger = LoggerFactory.getLogger(SysPreferencesServiceImpl.class);

    @Autowired
    private SysPreferencesMapper sysPreferencesMapper;

    /**
     * 保存首选项.
     * 
     * @param requestContext
     *            系统上下文
     * @param preferences
     *            首选项信息集合
     * @return List<SysPreferences> 返回保存结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SysPreferences> saveSysPreferences(IRequest requestContext, List<SysPreferences> preferences) {
        if (preferences.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("sysPreferences is null");
            }
        }
        for (SysPreferences sysPreferences : preferences) {
            // 判断该首选项信息是否存在，存在更新，否则新增
            if (self().selectUserPreference(sysPreferences.getPreferences(), sysPreferences.getUserId()) == null) {
                sysPreferencesMapper.insertSelective(sysPreferences);
            } else {
                sysPreferencesMapper.updatePreferLine(sysPreferences);
            }

        }
        return preferences;
    }

    /**
     * 查询首选项.
     * 
     * @param requestContext
     * @param preferences
     *            根据SysPreferences.accountId;SysPreferences.preferencesLevel查询条件
     *            查询当前首选项
     * @return
     */
    @Override
    public List<SysPreferences> querySysPreferences(IRequest requestContext, SysPreferences preferences) {
        return sysPreferencesMapper.select(preferences);
    }

    /**
     * 查询当前用户首选项集合
     * 
     * @param requestContext
     * @param preferences
     *            根据SysPreferences.accountId;SysPreferences.preferencesLevel查询条件
     *            查询当前首选项
     * @return
     */
    @Override
    public SysPreferences selectUserPreference(String preference, Long userId) {
        SysPreferences p = new SysPreferences();
        p.setPreferences(preference);
        p.setUserId(userId);
        return sysPreferencesMapper.selectUserPreference(p);
    }

}

/*
 * #{copyright}#
 */
package com.hand.hap.system.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.SysPreferences;

/**
 * 系统首选项Mapper.
 * 
 * @author zhangYang
 * @author njq.niu@hand-china.com
 */
public interface SysPreferencesMapper extends Mapper<SysPreferences> {

    SysPreferences selectUserPreference(SysPreferences record);

    int updatePreferLine(SysPreferences record);
}
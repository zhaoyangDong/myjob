/*
 * #{copyright}#
 */

package com.hand.hap.account.mapper;

import com.hand.hap.account.dto.UserRole;
import com.hand.hap.mybatis.common.Mapper;

/**
 * 角色分配功能Mapper.
 * 
 * @author xiawang.liu@hand-china.com
 */
public interface UserRoleMapper extends Mapper<UserRole> {

    int deleteByUserId(Long userId);

    int deleteByRecord(UserRole record);

    int deleteByRoleId(Long roleId);

}
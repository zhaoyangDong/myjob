/*
 * #{copyright}#
 */

package com.hand.hap.account.service;

import java.util.List;

import com.hand.hap.account.dto.UserRole;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

/**
 * 角色分配功能Service.
 * @author xiawang.liu@hand-china.com
 */
public interface IUserRoleService extends IBaseService<UserRole>, ProxySelf<IUserRoleService> {

    /**
     * 查询用户关联的所有角色.
     * @param requestContext  requestContext
     * @param role role
     * @return list
     */
    List<IRole> selectUserRoles(IRequest requestContext, UserRole role);

}
package com.hand.hap.account.service;

import java.util.List;

import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.RoleExt;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.RoleException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IRoleService extends IBaseService<Role>, ProxySelf<IRoleService> {

    /**
     * 查询,不属于当前用户角色的数据.
     *
     * @param requestContext
     *            请求上下文
     * @param roleExt
     *            条件,至少包含 userId
     * @param page
     *            起始页
     * @param pagesize
     *            页大小
     * @return 查询结果
     */
    List<IRole> selectRoleNotUserRoles(IRequest requestContext, RoleExt roleExt, int page, int pagesize);

    /**
     * 查询用户的所有角色.
     *
     * @param requestContext
     *            请求上下文
     * @param user
     *            包含 userId
     * @return 查询结果
     */
    List<IRole> selectRolesByUser(IRequest requestContext, User user);

    /**
     * 判断用户角色是否存在.
     *
     * @param userId
     * @param roleId
     * @throws RoleException
     */
    void checkUserRoleExists(Long userId, Long roleId) throws RoleException;

}

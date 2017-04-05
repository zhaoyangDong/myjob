package com.hand.hap.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.RoleExt;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.RoleException;
import com.hand.hap.account.mapper.RoleMapper;
import com.hand.hap.account.mapper.UserRoleMapper;
import com.hand.hap.account.service.IRole;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.core.IRequest;
import com.hand.hap.function.service.IRoleFunctionService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IRoleFunctionService roleFunctionService;

    /**
     * 查询被角色分配的功能以外的所有功能.D
     *
     * @author xiawang.liu@hand-china.com
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectRoleNotUserRoles(IRequest request, RoleExt roleExt, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List list = roleMapper.selectRoleNotUserRoles(roleExt);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectRolesByUser(IRequest requestContext, User user) {
        List rootRoles = roleMapper.selectUserRoles(user.getUserId());
        return rootRoles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void checkUserRoleExists(Long userId, Long roleId) throws RoleException {
        if (roleMapper.selectUserRoleCount(userId, roleId) != 1) {
            throw new RoleException(RoleException.MSG_INVALID_USER_ROLE, RoleException.MSG_INVALID_USER_ROLE, null);
        }
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public int deleteByPrimaryKey(Role record) {
        int ret = super.deleteByPrimaryKey(record);
        userRoleMapper.deleteByRoleId(record.getRoleId());
        roleFunctionService.clearRoleFunctionByRoleId(record.getRoleId());
        return ret;
    }
}

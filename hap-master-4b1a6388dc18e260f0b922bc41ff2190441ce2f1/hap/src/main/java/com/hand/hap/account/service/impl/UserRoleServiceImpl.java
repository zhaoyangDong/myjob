/*
 * #{copyright}#
 */
package com.hand.hap.account.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.mapper.UserRoleMapper;
import com.hand.hap.account.service.IRole;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.account.service.IUserRoleService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * 角色分配功能ServiceImpl.
 * 
 * @author xiawang.liu@hand-china.com
 */
@Transactional
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements IUserRoleService {

    private Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    @Qualifier("roleServiceImpl")
    private IRoleService roleService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectUserRoles(IRequest requestContext, UserRole role) {
        User u = new User();
        u.setUserId(role.getUserId());
        return roleService.selectRolesByUser(requestContext, u);
        // List<Role> selectUserRoles =
        // roleMapper.selectUserRoles(role.getUserId());
        // return selectUserRoles;
    }

    @Override
    public int deleteByPrimaryKey(UserRole record) {
        if (record.getSurId() != null)
            return super.deleteByPrimaryKey(record);
        return userRoleMapper.deleteByRecord(record);
    }
}
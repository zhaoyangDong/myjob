package com.hand.hap.account.mapper;

import java.util.List;

import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.RoleExt;
import com.hand.hap.account.dto.User;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface RoleMapper extends Mapper<Role> {

    List<Role> selectUserRoles(Long userId);

    List<Role> selectRoleNotUserRoles(RoleExt roleExt);

    List<Role> selectByUser(User user);

    List<Role> selectRolesByUserWithoutLang(User user);

    int selectUserRoleCount(Long userId, Long roleId);
}

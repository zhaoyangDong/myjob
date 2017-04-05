/*
 * #{copyright}#
 */

package com.hand.hap.function.mapper;

import java.util.List;
import java.util.Map;

import com.hand.hap.function.dto.RoleFunction;
import org.apache.ibatis.annotations.Param;
import com.hand.hap.mybatis.common.Mapper;

/**
 * 角色功能mapper.
 * 
 * @author wuyichu
 */
public interface RoleFunctionMapper extends Mapper<RoleFunction> {

    List<RoleFunction> selectRoleFunctions(RoleFunction record);

    List<Map<String, Object>> selectAllRoleResources();

    int deleteByFunctionId(Long functionId);

    int deleteByRoleId(Long roleId);

    int selectCountByFunctionCode(@Param("roleId") Long roleId, @Param("functionCode") String functionCode);
}
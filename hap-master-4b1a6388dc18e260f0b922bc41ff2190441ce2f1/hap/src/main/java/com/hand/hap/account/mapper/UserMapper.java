/*
 * #{copyright}#
 */

package com.hand.hap.account.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.account.dto.User;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author wuyichu
 */
public interface UserMapper extends Mapper<User> {

    User selectByUserName(String userName);

    List<User> selectByIdList(List<Long> userIds);

    int updatePassword(@Param("userId") Long userId,@Param("password") String passwordNew);
    
    int updateFirstLogin(@Param("userId") Long userId,@Param("status") String status);

    int updateBasic(User user);
}
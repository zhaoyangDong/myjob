/*
 * #{copyright}#
 */

package com.hand.hap.security.service;

import com.hand.hap.security.dto.User;

/**
 * 权限安全认证服务接口.
 * 
 * @author wuyichu
 * @deprecated
 */
public interface ISecurityService {

    boolean setUser(User user);

    boolean verifyUser(String url, String userName);
}

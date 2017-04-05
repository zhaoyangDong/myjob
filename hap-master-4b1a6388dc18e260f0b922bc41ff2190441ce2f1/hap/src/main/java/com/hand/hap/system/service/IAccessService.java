/*
 * #{copyright}#
 */
package com.hand.hap.system.service;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.core.ProxySelf;

/**
 * @author njq.niu@hand-china.com
 *
 *         2016年3月7日
 */
public interface IAccessService extends ProxySelf<IAccessService> {

   /**
    * 判断是否有权限访问.
    * 
    * @param requestContext
    * @param accessCode
    * @return access
    */
    boolean access(String accessCode);
    
    /**
     * 是否具有维护权限.
     * 
     * @return 是否具有权限
     */
    boolean accessMaintain();
    
    /**
     * 是否有范文功能的权限.
     * @author chenjingxiong
     * @return 是否具有权限.
     */
    boolean accessFunction(String functionCode);
    
    /**
     * 设置request.
     * 
     * @param request 请求上下文
     */
    void setRequest(HttpServletRequest request);
}

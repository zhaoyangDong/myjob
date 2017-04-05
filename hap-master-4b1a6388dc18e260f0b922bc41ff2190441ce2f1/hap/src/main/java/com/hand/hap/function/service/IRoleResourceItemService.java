/*
 * #{copyright}#
 */
package com.hand.hap.function.service;

import java.util.List;

import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.function.dto.RoleResourceItem;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年4月7日
 */
public interface IRoleResourceItemService extends ProxySelf<IRoleResourceItemService> {

   
    /**
     * 查询角色拥有的权限项.
     * 
     * @param requestContext
     * @param roleId
     * @param functionId
     * @return List
     */
    List<RoleResourceItem> queryRoleResourceItems(IRequest requestContext, Long roleId, Long functionId);
    
    
    
    /**
     * 保存角色拥有的权限项.
     * 
     * @param requestContext
     * @param roleResourceItems
     * @param roleId
     * @param functionId
     * @return
     */
    List<RoleResourceItem> batchUpdate(IRequest requestContext, @StdWho List<RoleResourceItem> roleResourceItems,
            Long roleId, Long functionId);

    /**
     * 判断是否拥有权限项.
     * 
     * @param roleId
     * @param resourceItemId
     * @return boolean
     */
    boolean hasResourceItem(Long roleId, Long resourceItemId);

    
}

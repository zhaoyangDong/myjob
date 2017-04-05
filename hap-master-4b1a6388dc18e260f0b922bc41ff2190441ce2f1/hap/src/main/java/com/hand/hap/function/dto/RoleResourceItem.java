/*
 * #{copyright}#
 */
package com.hand.hap.function.dto;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年4月8日
 */
public class RoleResourceItem extends ResourceItem {
    
    private static final long serialVersionUID = 1L;

    private Long rsiId;

    private Long roleId;

    private Long resourceItemId;

    /**
     * @return the rsiId
     */
    public Long getRsiId() {
        return rsiId;
    }

    /**
     * @param rsiId the rsiId to set
     */
    public void setRsiId(Long rsiId) {
        this.rsiId = rsiId;
    }

    /**
     * @return the roleId
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the resourceItemId
     */
    public Long getResourceItemId() {
        return resourceItemId;
    }

    /**
     * @param resourceItemId the resourceItemId to set
     */
    public void setResourceItemId(Long resourceItemId) {
        this.resourceItemId = resourceItemId;
    }

    
}
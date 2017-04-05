package com.hand.hap.account.dto;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class RoleExt extends Role {
    private Long surId;

    private Long userId;

    public Long getSurId() {
        return surId;
    }

    public void setSurId(Long surId) {
        this.surId = surId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

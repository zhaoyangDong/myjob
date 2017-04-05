/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.hap.account.service;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IRole {
    Long getRoleId();

    String getRoleCode();

    String getRoleName();

    boolean isEnabled();

    boolean isActive();
}

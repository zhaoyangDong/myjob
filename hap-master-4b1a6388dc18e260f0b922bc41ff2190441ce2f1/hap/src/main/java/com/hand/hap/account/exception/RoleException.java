/*
 * #{copyright}#
 */
package com.hand.hap.account.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * @author njq.niu@hand-china.com
 *
 * 2016年4月25日
 */
public class RoleException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 账号角色不存在.
     */
    public static final String MSG_INVALID_USER_ROLE = "error.account_role_invalid";
    
    /**
     * 非法参数.
     */
    public static final String MSG_INVALID_PARAMETER = "error.invalid_parameter";

    /**
     * 账号无有效角色.
     */
    public static final String MSG_NO_USER_ROLE = "error.account_no_role";
    
    public RoleException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}

/*
 * #{copyright}#
 */
package com.hand.hap.account.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * @author njq.niu@hand-china.com
 *
 *         2016年1月15日
 */
public class AdminNotDeleteException extends BaseException {

    private static final long serialVersionUID = 9046687211507280533L;
    
    private static final String MSG_ERROR_ADMIN_NOT_DELETE = "msg.error.user.admin.not_delete";

    public AdminNotDeleteException() {
        super(MSG_ERROR_ADMIN_NOT_DELETE, MSG_ERROR_ADMIN_NOT_DELETE, new Object[0]);
    }
}

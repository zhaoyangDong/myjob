package com.hand.hap.activiti.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class WflSecurityException extends BaseException {
    public static final String ERROR_CODE = "WFL_SECURITY_ERROR";

    public static final String NEED_ASSIGNEE_OR_ADMIN = "wfl.security.error.need_assignee_or_admin";

    public WflSecurityException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public WflSecurityException(String descriptionKey) {
        this(ERROR_CODE, descriptionKey, null);
    }
}
